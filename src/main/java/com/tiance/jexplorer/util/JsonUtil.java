package com.tiance.jexplorer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    private JsonUtil() {
    }

    public static String toJsonString(Object object) {
        String str = null;
        try {
            str = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
        }
        return str;
    }

    public static String toString(Object object) {
        return toJsonString(object);
    }

    public static <T> T parse(String json, Class<T> clazz) {
        T t = null;
        try {
            t = mapper.readValue(json, clazz);
        } catch (IOException e) {
        }
        return t;
    }
}
