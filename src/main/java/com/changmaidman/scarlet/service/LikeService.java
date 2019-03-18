package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.annotation.LikeHandler;
import com.changmaidman.scarlet.router.LikeRouter;
import com.changmaidman.scarlet.router.SentimentPairHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Qualifier("likeService")
public class LikeService extends SentimentService {

    public LikeService() {
        this.likesMap = new HashMap<>();
    }

    @Override
    public void processSentiment(SentimentService service) {

        Optional<SentimentPairHandler> sentimentHandler =
                LikeRouter.INSTANCE.getRegistryHandler(service);

        sentimentHandler
                .map(handler ->
                        CompletableFuture.runAsync(() ->
                                invokeSentimentHandler(handler)))
                .orElseGet(() -> CompletableFuture.completedFuture(null));
    }

    @LikeHandler
    public void process() {
        System.out.println("Like handler");
    }

    @LikeHandler
    public void process2() {
        System.out.println("Like handler2");
    }
}
