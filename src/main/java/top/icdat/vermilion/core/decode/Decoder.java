package top.icdat.vermilion.core.decode;


import top.icdat.vermilion.utils.reflect.TypeReflectUtils;

public interface Decoder<T> {

    default Class<?> getDecodeClass() {
        return TypeReflectUtils.getActualClasses(this.getClass())[0];
    }

    String decode(T t);

}
