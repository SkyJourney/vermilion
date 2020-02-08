package top.icdat.vermilion.core.execute;

import top.icdat.vermilion.annotation.Insert;
import top.icdat.vermilion.core.decode.DecoderExecutor;
import top.icdat.vermilion.utils.Converter;
import top.icdat.vermilion.utils.reflect.ClassReflectUtils;
import top.icdat.vermilion.utils.reflect.MethodReflectUtils;
import top.icdat.vermilion.utils.reflect.TypeReflectUtils;

import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractSqlGenerator implements SqlGenerator {

    @Override
    public String insertSql(Method method, Class<?> tableClass, Object[] args) {
        Insert insert = method.getAnnotation(Insert.class);
        String value = insert.value();
        InsertSqlBuilder insertSqlBuilder = InsertSqlBuilder.start();
        if (!"".equals(value)) {
            insertSqlBuilder.sql(customSql(value, args));
        } else {
            insertSqlBuilder.table(ClassReflectUtils.getTableName(tableClass))
                    .values(Converter.insertAssemble(method, tableClass, args));

        }
        return insertSqlBuilder.build();
    }

    protected String customSql(String template, Object[] args) {
        String sql = template;
        if (args!=null) {
            for (int i = 0; i < args.length; i++) {
                Class<?> argClass = args[i].getClass();
                sql = sql.replace("{%"+(i+1)+"}", DecoderExecutor.execute(argClass.cast(args[i])));
            }
        }

        return sql;
    }

}
