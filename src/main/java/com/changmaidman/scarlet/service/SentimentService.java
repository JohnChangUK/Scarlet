package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.router.SentimentPairHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public abstract class SentimentService {

    private static final Logger log = LoggerFactory.getLogger(SentimentService.class);

    Map<Integer, User> likesMap;

    public abstract void processSentiment(SentimentService service);

    void invokeSentimentHandler(SentimentPairHandler sentimentPairHandler) {
        Class<?> sentiment = sentimentPairHandler.getSentimentService();
        List<Method> methodList = sentimentPairHandler.getMethod();

        try {
            Object newClass = sentiment.newInstance();
            methodList.forEach(method -> {
                try {
                    method.invoke(newClass);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Error: ", e);
                }
            });
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Error: ", e);
        }
    }
}
