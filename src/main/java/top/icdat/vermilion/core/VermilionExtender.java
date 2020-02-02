package top.icdat.vermilion.core;

import top.icdat.vermilion.core.decode.Decoder;
import top.icdat.vermilion.core.decode.DecoderStorage;
import top.icdat.vermilion.core.encode.Encoder;
import top.icdat.vermilion.core.encode.EncoderStorage;

public class VermilionExtender {

    @SuppressWarnings("rawtypes")
    public static void registerDecoder(Decoder decoder) {
        DecoderStorage.registerDecoder(decoder);
    }

    public static <T> void unregisterDecoder(Class<T> clz) {
        DecoderStorage.unregisterDecoder(clz);
    }

    @SuppressWarnings("rawtypes")
    public static void registerEncoder(Encoder encoder) {
        EncoderStorage.registerEncoder(encoder);
    }

    public static <T> void unregisterEncoder(Class<T> clz) {
        EncoderStorage.unregisterEncoder(clz);
    }

}
