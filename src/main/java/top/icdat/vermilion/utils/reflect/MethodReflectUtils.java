package top.icdat.vermilion.utils.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodReflectUtils {

    public static Class<?> getReturnType(Method method, Map<String,Class<?>> geneticActualMap) {
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof Class) {
            return (Class<?>) returnType;
        } else if (returnType instanceof TypeVariable) {
            return geneticActualMap.get(returnType.getTypeName());
        } else if (returnType instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType)returnType).getRawType();
        } else {
            throw new RuntimeException();
        }
    }

    public static List<Class<?>> getArgsClasses(Object[] args) {
        return Stream.of(args)
                .map(Object::getClass)
                .collect(Collectors.toList());
    }

    public static Object[] replaceArg(Object[] args, int index, Object newArg) {
        Object[] objects = new Object[args.length];
        for (int i = 0 ; i < args.length ; i++) {
            if (i==index) {
                objects[i] = newArg;
            } else {
                objects[i] = args[i];
            }
        }
        return objects;
    }

}
