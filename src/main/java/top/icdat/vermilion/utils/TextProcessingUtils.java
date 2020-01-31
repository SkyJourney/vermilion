package top.icdat.vermilion.utils;

import java.lang.reflect.Field;

public class TextProcessingUtils {

    public static String getSetterName(Field field){
        String name = field.getName();
        if (field.getType().equals(boolean.class)) {
            if (name.indexOf("is")==0) {
                return "set"+name.substring(2);
            } else {
                return getSetString(name);
            }
        } else {
            return getSetString(name);
        }
    }

    private static String getSetString(String fieldName) {
        return "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
    }


}
