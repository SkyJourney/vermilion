package top.icdat.vermilion.core.execute;

import top.icdat.vermilion.annotation.*;
import top.icdat.vermilion.core.data.Operator;
import top.icdat.vermilion.core.session.Session;
import top.icdat.vermilion.exception.NotOperatorClassException;
import top.icdat.vermilion.utils.Converter;
import top.icdat.vermilion.utils.reflect.MethodReflectUtils;
import top.icdat.vermilion.utils.reflect.TypeReflectUtils;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SuppressWarnings("FieldCanBeLocal")
public class OperatorExecutor extends DefaultExecutor implements InvocationHandler {

    private Map<String,Class<?>> geneticActualMap;

    private OperatorExecutor(Session session, SqlGenerator sqlGenerator, Class<?> clz) {
        super(session, sqlGenerator);
        geneticActualMap = TypeReflectUtils.getGeneticActualMap(clz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxyOperator(Class<T> tClass,
                                         Session session,
                                         SqlGenerator sqlGenerator) {
        if (!Operator.class.isAssignableFrom(tClass)) {
            throw new NotOperatorClassException("Class ["+tClass.getName()
                    +"] is not an implementation of ["+ Operator.class.getName()+"].");
        } else {
            return (T) Proxy.newProxyInstance(
                    tClass.getClassLoader(),
                    new Class[]{tClass},
                    new OperatorExecutor(session,sqlGenerator,tClass)
            );
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            Class<?> returnClass = MethodReflectUtils.getReturnType(method, geneticActualMap);
            if (method.getAnnotation(Select.class)!=null) {
                return selectMethod(returnClass, method, args);
            } else if (method.getAnnotation(Insert.class)!=null){
                return insertMethod(returnClass, method, args);
            } else if (method.getAnnotation(Update.class)!=null){
                return null;
            } else if (method.getAnnotation(Delete.class)!=null){
                return null;
            } else {
                throw new RuntimeException();
            }
        }
    }

    private Object selectMethod(Class<?> returnClass, Method method, Object[] args) throws Throwable {
        if ("count".equals(method.getName())) {
            return select("SELECT count(0) num FROM (" +
                    getSqlGenerator().selectSql(
                            method,
                            geneticActualMap.get("T"),
                            args
                    ) + ") _count_t"
            ).get(0).get("num");
        }
        List<Map<String,Object>> results = select(
                getSqlGenerator().selectSql(
                        method,
                        geneticActualMap.get("T"),
                        args
                )
        );
        if (!"".equals(method.getAnnotation(Select.class).value())) {
            if (List.class.equals(returnClass)) {
                return results;
            } else if (Map.class.equals(returnClass)) {
                if (results.size()==1) {
                    return results.get(0);
                } else {
                    throw new RuntimeException();
                }
            } else {
                throw new RuntimeException();
            }
        } else {
            if (List.class.equals(returnClass)) {
                return results.stream()
                        .map(result -> Converter.resultConvert(geneticActualMap.get("T"),result))
                        .collect(Collectors.toList());
            } else if (geneticActualMap.get("T").equals(returnClass)) {
                if (results.size()==1) {
                    return Converter.resultConvert(geneticActualMap.get("T"),results.get(0));
                } else {
                    throw new RuntimeException();
                }
            } else {
                throw new RuntimeException();
            }
        }
    }

    private Object insertMethod(Class<?> returnClass, Method method, Object[] args) throws Throwable {
        List<Class<?>> argClasses = MethodReflectUtils.getArgsClasses(args);
        if (List.class.equals(returnClass)) {

            @SuppressWarnings("SuspiciousMethodCalls")
            int index =
                    Stream.of(method.getParameters())
                            .map(Parameter::getType)
                            .collect(Collectors.toList())
                            .indexOf(List.class);
            @SuppressWarnings("rawtypes")
            List modelList = (List) args[index];
            //noinspection unchecked
            return modelList.stream().map(model -> geneticActualMap.get("T").cast(model))
                    .peek(model -> {
                        try {
                            insert(
                                    getSqlGenerator().insertSql(
                                            method,
                                            geneticActualMap.get("T"),
                                            MethodReflectUtils.replaceArg(args, index, model)
                                    )
                            );
                            setAutoKey(lastId(), model);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    })
                    .collect(Collectors.toList());
        } else if (geneticActualMap.get("T").equals(returnClass)) {
            insert(
                    getSqlGenerator().insertSql(
                            method,
                            geneticActualMap.get("T"),
                            args
                    )
            );
            Object result = Converter.insertAssemble(method, geneticActualMap.get("T"), args);
            setAutoKey(lastId(),result);
            return result;
        } else if (boolean.class.equals(returnClass) || Boolean.class.equals(returnClass)) {
            return insert(
                    getSqlGenerator().insertSql(
                            method,
                            geneticActualMap.get("T"),
                            args
                    )
            )>0;
        } else if (void.class.equals(returnClass)) {
            insert(
                    getSqlGenerator().insertSql(
                            method,
                            geneticActualMap.get("T"),
                            args
                    )
            );
            setAutoKey(
                    lastId(),
                    Converter.insertAssemble(method, geneticActualMap.get("T"), args)
            );
            return null;
        } else {
            throw new RuntimeException();
        }
    }

    private <T> void  setAutoKey(Object autoKey, T t) {
        Field setField = Stream.of(t.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(AutoIncrement.class)!=null)
                .findFirst().orElse(null);
        if (setField!=null) {
            setField.setAccessible(true);
            try {
                setField.set(t, autoKey);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
