package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.annotation.DislikeHandler;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.router.DislikeRouter;
import com.changmaidman.scarlet.router.SentimentPairHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class DislikeService extends SentimentService {

    private Map<Integer, User> dislikesMap;

    public DislikeService() {
        this.dislikesMap = new HashMap<>();
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
