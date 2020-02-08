package top.icdat.vermilion.core.execute;

import java.lang.reflect.Method;

public interface SqlGenerator {

    String insertSql(Method method, Class<?> tableClass, Object[] args);

    String deleteSql(Method method, Class<?> tableClass, Object[] args);

    String updateSql(Method method, Class<?> tableClass, Object[] args);

    String selectSql(Method method, Class<?> tableClass, Object[] args);

}
