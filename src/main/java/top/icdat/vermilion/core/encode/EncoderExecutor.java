package top.icdat.vermilion.core.encode;

import top.icdat.vermilion.exception.NoSuchEncoderException;

public class EncoderExecutor {

    @SuppressWarnings({"rawtypes","unchecked"})
    public static <T> T execute(Object obj, Class<T> clz) {
        Encoder encoder = EncoderStorage.getEncoder(clz);
        if (encoder==null) {
            throw new NoSuchEncoderException("There is no encoder registered for class ["+clz.getName()+"]");
        }
        return ((Encoder<T>)encoder).encode(obj);
    }


}
