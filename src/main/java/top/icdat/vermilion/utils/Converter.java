package top.icdat.vermilion.utils;

import top.icdat.vermilion.core.data.Criteria;
import top.icdat.vermilion.core.data.Primary;
import top.icdat.vermilion.exception.InstantiatedException;
import top.icdat.vermilion.utils.reflect.FieldReflectUtils;
import top.icdat.vermilion.utils.reflect.MethodReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Converter {

    public static <T> T resultConvert(Class<T> clz, Map<String,Object> singleResult) {
        T t = null;
        try {
            t = clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Field[] fields = FieldReflectUtils.getColumnFields(clz);
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                field.set(t, singleResult.get(FieldReflectUtils.getColumnName(field)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    public static <T> T insertAssemble(Method method, Class<T> clz, Object[] args) {
        T t;
        if (args!=null) {
            List<Class<?>> argClasses = MethodReflectUtils.getArgsClasses(args);
            if (argClasses.contains(clz)) {
                t = clz.cast(args[argClasses.indexOf(clz)]);
            } else {
                try {
                    t = clz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                    throw new InstantiatedException("Cannot create the instance for ["+clz.getName()+"] class.");
                }
            }
            assembleFromArgs(t, method.getParameters(), args);
            return t;
        } else {
            throw new InstantiatedException("Cannot create the instance for ["+clz.getName()+"] class.");
        }
    }

    public static  <T> Criteria<T> selectAssemble(Method method, Class<T> clz, Object[] args) {
        Criteria<T> criteria;
        if (args!=null) {
            List<Class<?>> argClasses = MethodReflectUtils.getArgsClasses(args);
            if (argClasses.contains(clz)) {
                criteria = Criteria.of(clz.cast(args[argClasses.indexOf(clz)]));
            } else if (argClasses.contains(Criteria.class)) {
                //noinspection unchecked
                criteria = (Criteria<T>) args[argClasses.indexOf(Criteria.class)];
            } else if (argClasses.contains(Primary.class)) {
                //noinspection unchecked
                criteria = Criteria.of(
                        ((Primary<T>) args[argClasses.indexOf(Primary.class)]).getInclusion()
                );
            } else {
                criteria = Criteria.of(clz);
            }
            assembleFromArgs(criteria.getInclusion(), method.getParameters(), args);
        } else {
            criteria = Criteria.of(clz);
        }
        return criteria;
    }

    public static <T> void assembleFromArgs(T t, Parameter[] parameters, Object[] args) {
        List<Field> fields = Arrays.asList(FieldReflectUtils.getColumnFields(t.getClass()));
        for (int i = 0; i < parameters.length; i++) {
            Field field = null;
            if ("primaryKey".equals(parameters[i].getName())) {
                if (!Primary.class.equals(parameters[i].getType())) {
                    field = fields.stream()
                            .filter(aField -> aField.getAnnotation(top.icdat.vermilion.annotation.Primary.class)!=null)
                            .findFirst().orElse(null);
                }
            } else {
                field = FieldReflectUtils.getFieldByName(parameters[i].getName(), fields);
            }
            if (field!=null) {
                field.setAccessible(true);
                try {
                    field.set(t, args[i]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
