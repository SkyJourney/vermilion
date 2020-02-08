package top.icdat.vermilion.core.execute;

import top.icdat.vermilion.annotation.*;
import top.icdat.vermilion.utils.Converter;
import top.icdat.vermilion.utils.reflect.ClassReflectUtils;
import top.icdat.vermilion.utils.reflect.FieldReflectUtils;

import java.lang.reflect.Method;

public class MySqlGenerator extends AbstractSqlGenerator {

    @Override
    public String deleteSql(Method method, Class<?> tableClass, Object[] args) {
        return null;
    }

    @Override
    public String updateSql(Method method, Class<?> tableClass, Object[] args) {
        return null;
    }

    @Override
    public String selectSql(Method method, Class<?> tableClass, Object[] args) {
        Select select = method.getAnnotation(Select.class);
        String value = select.value();
        SelectSqlBuilder selectSqlBuilder = MySelectSqlBuilder.start();
        if (!"".equals(value)) {
            selectSqlBuilder.sql(customSql(value, args));
            if (method.getAnnotation(Group.class)!=null) {
                selectSqlBuilder.group(method.getAnnotation(Group.class));
            }
        } else {
            selectSqlBuilder.table(ClassReflectUtils.getTableName(tableClass))
                    .columns(FieldReflectUtils.getAllFields(tableClass))
                    .criteria(Converter.selectAssemble(method, tableClass, args));
        }
        if (method.getAnnotation(Sort.class)!=null) {
            selectSqlBuilder.sort(method.getAnnotation(Sort.class));
        }
        return selectSqlBuilder.build();
    }

}
