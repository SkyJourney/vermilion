package top.icdat.vermilion.data;


import top.icdat.vermilion.exception.InstantiatedException;
import top.icdat.vermilion.exception.InvocationException;
import top.icdat.vermilion.exception.NoSuchFieldException;
import top.icdat.vermilion.exception.NoSuchMethodException;
import top.icdat.vermilion.utils.TextProcessingUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Criteria<T> extends Container<T> {

    private Criteria(T t) {
        super(t);
    }

    public static <T> Criteria<T> newCriteria(Class<T> tableModel) {
        try {
            return new Criteria<T>(tableModel.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new InstantiatedException("Cannot create Criteria instance for ["+tableModel.getName()+"] class.");
        }
    }

    public Criteria<T> setCriterion(String field, Object value) {
        Field setField;
        try {
            setField = getInclusion().getClass().getField(field);
        } catch (java.lang.NoSuchFieldException e) {
            e.printStackTrace();
            throw new NoSuchFieldException("No such field in class["+getInclusion().getClass().getName()+"].");
        }
        Class<?> fieldType = setField.getType();
        Method setMethod;
        try {
            setMethod = getInclusion().getClass().getMethod(TextProcessingUtils.getSetterName(setField), fieldType);
        } catch (java.lang.NoSuchMethodException e) {
            e.printStackTrace();
            throw new NoSuchMethodException("Cannot find setter method for field ["+field+"].") ;
        }
        try {
            setMethod.invoke(getInclusion(), fieldType.cast(value));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new InvocationException("Occur an exception when invoking setter method. Check the value type.");
        }
        return this;
    }
}
