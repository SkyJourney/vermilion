package top.icdat.vermilion.core.data;

import top.icdat.vermilion.exception.InstantiatedException;
import top.icdat.vermilion.exception.MissingPrimaryKeyException;
import top.icdat.vermilion.exception.NoSuchFieldException;
import top.icdat.vermilion.utils.reflect.FieldReflectUtils;

import java.lang.reflect.Field;
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

    public static <T> Primary<T> of(Class<T> tableModel) {
        try {
            return new Primary<T>(tableModel.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new InstantiatedException("Cannot create Primary instance for ["+tableModel.getName()+"] class.");
        }
    }

    private void initPrimaries() {
        Field[] fields = getT().getClass().getDeclaredFields();
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
        return !primaries.containsValue(Boolean.FALSE);
    }

    @Override
    public T getInclusion() {
        if (isPrimariesFullSet()) {
            return super.getInclusion();
        } else {
            throw new MissingPrimaryKeyException("The primary keys of ["+getT().getClass()+"] are not filled.");
        }
    }
}
