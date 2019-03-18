package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.model.Sentiment;

import java.lang.reflect.Method;
import java.util.List;

public class SentimentHandler {

    private final Class<? extends Sentiment> sentiment;
    private final List<Method> method;

    public SentimentHandler(Class<? extends Sentiment> sentiment, List<Method> method) {
        this.sentiment = sentiment;
        this.method = method;
    }

    public Class<? extends Sentiment> getSentiment() {
        return sentiment;
    }

    public List<Method> getMethod() {
        return method;
    }
}
