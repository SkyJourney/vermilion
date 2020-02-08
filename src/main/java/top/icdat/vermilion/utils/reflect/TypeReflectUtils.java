package top.icdat.vermilion.utils.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TypeReflectUtils {

    public static Class<?>[] getActualClasses(Class<?> clz) {
        return Stream.of(clz.getGenericInterfaces())
                .filter(type -> type instanceof ParameterizedType)
                .flatMap(type -> {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    Type[] actualTypes = parameterizedType.getActualTypeArguments();
                    return Stream.of(actualTypes).map(actualType -> {
                        try {
                            return Class.forName(actualType.getTypeName());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            throw new RuntimeException();
                        }
                    });
                })
                .toArray(Class<?>[]::new);
    }

    public static Type[] getGeneticTypes(Class<?> clz) {
        return Stream.of(clz.getInterfaces())
                .filter(clazz -> clazz.getTypeParameters().length!=0)
                .flatMap(clazz -> Stream.of(clazz.getTypeParameters()))
                .toArray(Type[]::new);
    }

    public static Map<String, Class<?>> getGeneticActualMap(Class<?> clz) {
        Type[] types = getGeneticTypes(clz);
        Class<?>[] classes = getActualClasses(clz);
        Map<String,Class<?>> geneticActualMap = new HashMap<>();
        for (int i = 0 ; i < types.length ; i++) {
            geneticActualMap.put(types[i].getTypeName(),classes[i]);
        }
        return geneticActualMap;
    }

}
