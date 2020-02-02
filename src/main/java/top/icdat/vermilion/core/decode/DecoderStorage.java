package top.icdat.vermilion.core.decode;

import java.util.HashMap;
import java.util.Map;

public class DecoderStorage {

    @SuppressWarnings({"rawtypes"})
    private static final Map<Class<? extends Decoder>,Decoder> DECODER_MAP = new HashMap<>();

    static {
        registerDecoder(new IntegerDecoder());
        registerDecoder(new StringDecoder());
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    public static void registerDecoder(Decoder decoder){
        DECODER_MAP.put(decoder.getDecodeClass(), decoder);
    }

    @SuppressWarnings({"unchecked"})
    public static <T> Decoder<T> getDecoder(Class<T> clz) {
        return DECODER_MAP.get(clz);
    }

    public static <T> void unregisterDecoder(Class<T> clz) {
        DECODER_MAP.remove(clz);
    }

}
