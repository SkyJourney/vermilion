package top.icdat.vermilion.core.encode;

import top.icdat.vermilion.utils.reflect.TypeReflectUtils;

public interface Encoder<T> {

    default Class<?> getEncodeClass() {
        return TypeReflectUtils.getActualClasses(this.getClass())[0];
    }

    T encode(Object obj);
}
