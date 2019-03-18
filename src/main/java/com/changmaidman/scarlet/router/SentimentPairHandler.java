package com.changmaidman.scarlet.router;

import java.lang.reflect.Method;
import java.util.List;

public class SentimentPairHandler {

    private final Class<?> sentimentService;
    private final List<Method> method;

    public SentimentPairHandler(Class<?> sentimentService, List<Method> method) {
        this.sentimentService = sentimentService;
        this.method = method;
    }

    public Class<?> getSentimentService() {
        return sentimentService;
    }

    public List<Method> getMethod() {
        return method;
    }
}
