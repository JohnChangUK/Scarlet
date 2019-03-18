package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.annotation.DislikeHandler;
import com.changmaidman.scarlet.router.DislikeRouter;
import com.changmaidman.scarlet.router.SentimentPairHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Qualifier("dislikeService")
public class DislikeService extends SentimentService {

    public DislikeService() {
        this.likesMap = new HashMap<>();
    }

    @Override
    public void processSentiment(SentimentService service) {

        Optional<SentimentPairHandler> sentimentHandler =
                DislikeRouter.INSTANCE.getRegistryHandler(service);

        sentimentHandler
                .map(handler ->
                        CompletableFuture.runAsync(() ->
                                invokeSentimentHandler(handler)))
                .orElseGet(() -> CompletableFuture.completedFuture(null));
    }

    @DislikeHandler
    public void processDislike() {
        System.out.println("Dislike handler invoked");
    }

    @DislikeHandler
    public void storeDislikeInDatabase() {
        System.out.println("Dislike action stored in database");
    }
}
