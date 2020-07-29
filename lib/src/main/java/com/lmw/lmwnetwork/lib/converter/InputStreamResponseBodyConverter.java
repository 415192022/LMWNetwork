package com.lmw.lmwnetwork.lib.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class InputStreamResponseBodyConverter <T> implements Converter<ResponseBody, T> {

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            return (T) value.byteStream();
        } catch (Exception e) {
            return null;
        }
    }
}
