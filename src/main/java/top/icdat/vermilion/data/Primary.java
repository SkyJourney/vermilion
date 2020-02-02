package top.icdat.vermilion.data;

import top.icdat.vermilion.exception.InstantiatedException;
import top.icdat.vermilion.exception.InvocationException;
import top.icdat.vermilion.exception.NoSuchFieldException;
import top.icdat.vermilion.exception.NoSuchMethodException;
import top.icdat.vermilion.utils.TextProcessingUtils;
import top.icdat.vermilion.utils.FieldReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Primary<T> extends Container<T> {

    private Map<Field,Boolean> primaries;

    private Primary(T t) {
        super(t);
        initPrimaries();
    }

    public static <T> Primary<T> newPrimary(Class<T> tableModel) {
        try {
            return new Primary<T>(tableModel.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new InstantiatedException("Cannot create Primary instance for ["+tableModel.getName()+"] class.");
        }
    }

    private void initPrimaries() {
        Field[] fields = getInclusion().getClass().getDeclaredFields();
        primaries = Arrays.stream(fields)
                .filter(field -> field.getAnnotation(top.icdat.vermilion.annotation.Primary.class)!=null)
                .collect(Collectors.toMap(field -> field, field -> Boolean.FALSE,(f1, f2) -> f1, HashMap::new));
    }

    public Primary<T> setPrimary(String field, Object value) {
        Field setField = FieldReflectUtils.getFieldByName(field, primaries.keySet());
        if (setField!=null) {
            setFieldValue(setField, value);
            primaries.put(setField, Boolean.TRUE);
        } else {
            throw new NoSuchFieldException("No such PrimaryKey named as ["+field+"].");
        }
        return this;
    }

    public boolean isPrimariesFullSet() {
        return primaries.containsValue(Boolean.FALSE);
    }

}
