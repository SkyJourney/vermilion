package top.icdat.vermilion.core.encode;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes","unchecked"})
public class EncoderStorage {

    private static final Map<Class<? extends Encoder>,Encoder> ENCODER_MAP = new HashMap<>();

    static {
        registerEncoder(new IntegerEncoder());
    }

    public static void registerEncoder(Encoder encoder){
        ENCODER_MAP.put(encoder.getEncodeClass(), encoder);
    }

    public static <T> Encoder<T> getEncoder(Class<T> clz) {
        return ENCODER_MAP.get(clz);
    }

    public static <T> void unregisterEncoder(Class<T> clz) {
        ENCODER_MAP.remove(clz);
    }

}
