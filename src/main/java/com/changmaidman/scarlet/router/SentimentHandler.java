package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.model.Sentiment;

import java.lang.reflect.Method;

public class SentimentHandler {

    private final Class<? extends Sentiment> sentiment;
    private final Method method;

    public SentimentHandler(Class<? extends Sentiment> sentiment, Method method) {
        this.sentiment = sentiment;
        this.method = method;
    }

    public Class<? extends Sentiment> getSentiment() {
        return sentiment;
    }

    public Method getMethod() {
        return method;
    }
}
