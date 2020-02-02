package top.icdat.vermilion.core.decode;

import top.icdat.vermilion.exception.NoSuchDecoderException;

public class DecoderExecutor {

    @SuppressWarnings({"rawtypes","unchecked"})
    public static <T> String execute(T t) {
        Decoder decoder = DecoderStorage.getDecoder(t.getClass());
        if (decoder==null) {
            throw new NoSuchDecoderException("There is no decoder registered for class ["+t.getClass().getName()+"]");
        }
        return ((Decoder<T>)decoder).decode(t);
    }


}
