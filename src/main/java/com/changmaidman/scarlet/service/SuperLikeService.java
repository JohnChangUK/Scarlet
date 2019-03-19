package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.model.SentimentRegistry;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.router.SuperLikeRouter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class SuperLikeService extends SentimentService {

    private Map<Integer, User> superLikesMap;

    public SuperLikeService() {
        this.superLikesMap = new HashMap<>();
    }

    @Override
    public void processSentiment(SentimentService service) {

        List<SentimentRegistry> sentimentRegistryList =
                SuperLikeRouter.INSTANCE.getSentimentRegistry();

        sentimentRegistryList
                .forEach(sentimentRegistry ->
                        CompletableFuture.runAsync(() ->
                                invokeSentimentRegistry(sentimentRegistry)));
    }
}
