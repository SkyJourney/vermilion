package top.icdat.vermilion.utils;

import java.lang.reflect.Field;
import java.util.Collection;

public class VermilionReflectUtils {

    public static Field getFieldByName(String fieldName, Collection<Field> fields) {
        for(Field field : fields) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

}
