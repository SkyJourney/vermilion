package top.icdat.vermilion.utils.reflect;

import com.google.common.base.CaseFormat;
import top.icdat.vermilion.annotation.Column;
import top.icdat.vermilion.annotation.NonNull;
import top.icdat.vermilion.annotation.Primary;
import top.icdat.vermilion.core.decode.DecoderExecutor;
import top.icdat.vermilion.exception.DataTypeException;
import top.icdat.vermilion.exception.NoSuchFieldException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class FieldReflectUtils {

    public static Field getFieldByName(String fieldName, Collection<Field> fields) {
        for(Field field : fields) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    public static List<String> getAllFields(Class<?> clz) {
        Field[] fields = getColumnFields(clz);
        List<String> fieldList = new ArrayList<>();
        for (Field field : fields) {
            fieldList.add(getColumnSqlName(field));
        }
        return fieldList;
    }

    public static <T> Map<String,String> getNotNullFields(T t) {
        return getNotNullFields(t,false);
    }

    public static <T> Map<String,String> getNotNullFields(T t, boolean checkNotNull) {
        Field[] fields = getColumnFields(t.getClass());
        Map<String,String> criteria = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object obj;
            try {
                obj = field.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(),e);
            }
            if (obj !=null) {
                criteria.put(getColumnSqlName(field),getColumnValue(typeConverter(field.getType(), obj)));
            } else if (checkNotNull && field.getAnnotation(NonNull.class)!=null) {
                throw new RuntimeException();
            }
        }
        return criteria;
    }

    public static Field[] getColumnFields(Class<?> clz) {
        Field[] fields = clz.getDeclaredFields();
        return Stream.of(fields)
                .filter(field ->
                        field.getAnnotation(Primary.class)!=null ||
                                field.getAnnotation(Column.class)!=null)
                .toArray(Field[]::new);
    }

    public static String getColumnSqlName(Field field) {
        return "`" + getColumnName(field) + "`";
    }

    private static <T> String getColumnValue(T t) {
        return DecoderExecutor.execute(t);
    }

    private static String getColumnName0(String value, Field field) {
        if("".equals(value)) {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
        } else {
            return value;
        }
    }

    public static <T> T typeConverter(Class<T> tClass, Object object) {
        if (tClass.equals(int.class)
                || tClass.equals(byte.class)
                || tClass.equals(boolean.class)
                || tClass.equals(long.class)
                || tClass.equals(short.class)
                || tClass.equals(float.class)
                || tClass.equals(char.class)) {
            throw new DataTypeException("Cannot use primitive types in POJO class.");
        }
        return tClass.cast(object);
    }

    public static String getColumnName(Field field) {
        Primary primary = field.getAnnotation(Primary.class);
        if (primary!=null) {
            return getColumnName0(primary.value(),field);
        }
        Column column = field.getAnnotation(Column.class);
        if (column!=null) {
            return getColumnName0(column.value(),field);
        }
        throw new RuntimeException();
    }

    public static Field requiredExists(Class<?> clz, String fieldName) {
        try {
            return clz.getDeclaredField(fieldName);
        } catch (java.lang.NoSuchFieldException e) {
            throw new NoSuchFieldException("No such field in class["+clz.getName()+"].",e);
        }
    }

}
