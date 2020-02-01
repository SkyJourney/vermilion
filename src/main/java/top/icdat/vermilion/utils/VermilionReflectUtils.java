package top.icdat.vermilion.utils;

import com.google.common.base.CaseFormat;
import top.icdat.vermilion.annotation.Column;
import top.icdat.vermilion.annotation.Primary;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class VermilionReflectUtils {

    public static Field getFieldByName(String fieldName, Collection<Field> fields) {
        for(Field field : fields) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    public static <T> Map<String,String> getNotNullFields(T t) {
        Field[] fields = getColumnFields(t);
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
                criteria.put(getColumnName(field),getColumnValue(field.getType().cast(obj)));
            }
        }
        return criteria;
    }

    private static <T> Field[] getColumnFields(T t) {
        Field[] fields = t.getClass().getFields();
        return Stream.of(fields)
                .filter(field ->
                        field.getAnnotation(Primary.class)!=null ||
                                field.getAnnotation(Column.class)!=null)
                .toArray(Field[]::new);
    }

    private static String getColumnName(Field field) {
        Primary primary = field.getAnnotation(Primary.class);
        if (primary!=null) {
            return "`" + getColumnName0(primary.value(),field) + "`";
        }
        Column column = field.getAnnotation(Column.class);
        if (column!=null) {
            return "`" + getColumnName0(column.value(),field) + "`";
        }
        throw new RuntimeException();
    }

    private static <T> String getColumnValue(T t) {
        return null;
    }

    private static String getColumnName0(String value, Field field) {
        if("".equals(value)) {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
        } else {
            return value;
        }
    }

}
