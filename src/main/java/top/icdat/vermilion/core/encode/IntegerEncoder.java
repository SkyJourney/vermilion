package top.icdat.vermilion.core.encode;

public class IntegerEncoder implements Encoder<Integer> {
    @Override
    public Class<Integer> getEncodeClass() {
        return Integer.class;
    }

    @Override
    public Integer encode(Object obj) {
        return (Integer)obj;
    }
}
