package top.icdat.vermilion.core.data;

import top.icdat.vermilion.exception.InvocationException;
import top.icdat.vermilion.utils.reflect.FieldReflectUtils;

import java.lang.reflect.Field;

public abstract class Container<T> implements Includable<T> {

    private T t;

    public Container(T t) {
        this.t = t;
    }

    protected T getT() {
        return t;
    }

    @Override
    public T getInclusion() {
        return t;
    }

    protected void setFieldValue(Field field, Object value) {
        Class<?> fieldType = field.getType();
        field.setAccessible(true);
        try {
            field.set(getInclusion(), FieldReflectUtils.typeConverter(fieldType, value));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new InvocationException("Occur an exception when invoking setter method. Check the value type.");
        }
    }
}
