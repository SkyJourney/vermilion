package top.icdat.vermilion.utils.reflect;

import com.google.common.base.CaseFormat;
import top.icdat.vermilion.annotation.Table;
import top.icdat.vermilion.exception.NotTableClassException;

public class ClassReflectUtils {

    public static boolean doesClassExist(String name) {
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName(name);
            if (c != null)
                return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return false;
    }

    public static String getTableName(Class<?> clz) {
        Table table = clz.getAnnotation(Table.class);
        if (table!=null) {
            if ("".equals(table.value())) {
                return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,clz.getSimpleName());
            } else {
                return table.value();
            }
        } else {
            throw new NotTableClassException("Class ["+clz.getName()+"] is not annotated by @table");
        }
    }

}
