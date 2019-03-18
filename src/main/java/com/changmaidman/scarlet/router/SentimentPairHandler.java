package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.model.Sentiment;
import com.changmaidman.scarlet.service.SentimentService;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public class SentimentPairHandler {

    private final Class<? extends SentimentService> sentimentService;
    private final List<Method> method;

    public SentimentPairHandler(Class<? extends SentimentService> sentimentService, List<Method> method) {
        this.sentimentService = sentimentService;
        this.method = method;
    }

    public Class<? extends SentimentService> getSentimentService() {
        return sentimentService;
    }

    public List<Method> getMethod() {
        return method;
    }
}
