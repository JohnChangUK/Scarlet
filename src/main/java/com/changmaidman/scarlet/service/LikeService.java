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
    public void processLike() {
        System.out.println("Process like");
    }

    @LikeHandler
    public void processSuperLike() {
        System.out.println("Process super like");
    }

    @LikeHandler
    public void storeLikeInDatabase() {
        System.out.println("Like action stored in database");
    }

    @LikeHandler
    public void storeSuperLikeInDatabase() {
        System.out.println("Super like action stored in database");
    }
}
