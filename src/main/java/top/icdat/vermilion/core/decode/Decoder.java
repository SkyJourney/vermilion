package top.icdat.vermilion.core.decode;


public interface Decoder<T> {

    Class<T> getDecodeClass();

    String decode(T t);

}
