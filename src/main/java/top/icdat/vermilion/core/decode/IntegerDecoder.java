package top.icdat.vermilion.core.decode;

public class IntegerDecoder implements Decoder<Integer> {

    @Override
    public String decode(Integer integer) {
        return String.valueOf(integer);
    }
}
