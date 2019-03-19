package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.annotation.SuperLikeHandler;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.router.SentimentPairHandler;
import com.changmaidman.scarlet.router.SuperLikeRouter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class SuperLikeService extends SentimentService {

    private Map<Integer, User> superLikesMap;

    public SuperLikeService() {
        this.superLikesMap = new HashMap<>();
    }

    @Override
    public void processSentiment(SentimentService service) {

        Optional<SentimentPairHandler> sentimentHandler =
                SuperLikeRouter.INSTANCE.getRegistryHandler(service);

        sentimentHandler
                .map(handler ->
                        CompletableFuture.runAsync(() ->
                                invokeSentimentHandler(handler)))
                .orElseGet(() -> CompletableFuture.completedFuture(null));
    }

    @SuperLikeHandler
    public void processSuperLike() {
        System.out.println("Process super like");
    }

    @SuperLikeHandler
    public void storeSuperLikeInDatabase() {
        System.out.println("Super like action stored in database");
    }
}
