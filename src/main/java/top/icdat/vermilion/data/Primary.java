package top.icdat.vermilion.data;

import top.icdat.vermilion.exception.InstantiatedException;
import top.icdat.vermilion.exception.InvocationException;
import top.icdat.vermilion.exception.NoSuchMethodException;
import top.icdat.vermilion.utils.TextProcessingUtils;
import top.icdat.vermilion.utils.VermilionReflectUtils;

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
        Field[] fields = getInclusion().getClass().getFields();
        primaries = Arrays.stream(fields)
                .filter(field -> field.getAnnotation(top.icdat.vermilion.annotation.Primary.class)!=null)
                .collect(Collectors.toMap(field -> field, field -> Boolean.FALSE,(f1, f2) -> f1, HashMap::new));
    }

    public Primary<T> setPrimary(String field, Object value) {
        Field field1 = VermilionReflectUtils.getFieldByName(field, primaries.keySet());
        if (field1!=null) {
            Method method;
            try {
                method = getInclusion().getClass().getMethod(TextProcessingUtils.getSetterName(field1),field1.getType());
            } catch (java.lang.NoSuchMethodException e) {
                e.printStackTrace();
                throw new NoSuchMethodException("Cannot find setter method for field ["+field+"].");
            }
            try {
                method.invoke(getInclusion(), value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new InvocationException("Occur an exception when invoking setter method. Check the value type.");
            }
            primaries.put(field1, Boolean.TRUE);
        } else {
            throw new RuntimeException();
        }
        return this;
    }

    public boolean isPrimariesFullSet() {
        return primaries.containsValue(Boolean.FALSE);
    }

}
