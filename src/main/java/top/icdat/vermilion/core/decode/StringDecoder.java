package top.icdat.vermilion.core.decode;

public class StringDecoder implements Decoder<String> {
    @Override
    public Class<String> getDecodeClass() {
        return String.class;
    }

    @Override
    public String decode(String s) {
        return "'"+s+"'";
    }
}
