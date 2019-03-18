package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.annotation.DislikeHandler;
import com.changmaidman.scarlet.annotation.LikeHandler;
import com.changmaidman.scarlet.model.Sentiment;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.router.LikeRouter;
import com.changmaidman.scarlet.router.SentimentHandler;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class DislikeService extends SentimentService {

    Map<Integer, User> likesMap;

    public CompletableFuture<Void> processLike(Sentiment sentiment) {
        Optional<SentimentHandler> likeHandler = LikeRouter.INSTANCE.getSentimentHandler(sentiment);

        return likeHandler
                .map(sentimentHandler ->
                        CompletableFuture.runAsync(() -> invokeLikeHandler(sentiment, sentimentHandler)))
                .orElseGet(() -> CompletableFuture.completedFuture(null));
    }

    private void invokeLikeHandler(Sentiment sentiment, SentimentHandler sentimentHandler) {
        Class<? extends Sentiment> likeSentiment = sentimentHandler.getSentiment();
        List<Method> likeMethods = sentimentHandler.getMethod();

        try {
            Object newClass = likeSentiment.newInstance();
            likeMethods.forEach(method -> {
                try {
                    method.invoke(newClass);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @DislikeHandler
    public void process() {
        System.out.println("Dislike handler");
    }

    @DislikeHandler
    public void process2() {
        System.out.println("Dislike handler2");
    }
}
