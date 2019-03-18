package com.changmaidman.scarlet.router;

import java.lang.reflect.Method;
import java.util.List;

public class SentimentPairHandler {

    private final Class<?> clazz;
    private final List<Method> method;

    SentimentPairHandler(Class<?> clazz, List<Method> method) {
        this.clazz = clazz;
        this.method = method;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public List<Method> getMethod() {
        return method;
    }
}
