package top.icdat.vermilion.core.encode;

public interface Encoder<T> {

    Class<T> getEncodeClass();

    T encode(Object obj);
}
