package top.icdat.vermilion.core.decode;

public class IntegerDecoder implements Decoder<Integer> {

    @Override
    public Class<Integer> getDecodeClass() {
        return Integer.class;
    }

    @Override
    public String decode(Integer integer) {
        return String.valueOf(integer);
    }
}
