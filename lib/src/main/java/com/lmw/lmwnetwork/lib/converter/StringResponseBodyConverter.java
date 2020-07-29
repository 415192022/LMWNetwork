package com.lmw.lmwnetwork.lib.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author cw
 */

public class StringResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            return (T) new String(value.bytes());
        } catch (Exception e) {
            return null;
        }
    }
}
