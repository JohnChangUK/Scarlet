package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.annotation.LikeHandler;
import com.changmaidman.scarlet.model.SentimentRegistry;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.router.LikeRouter;
import com.changmaidman.scarlet.router.SentimentPairHandler;
import com.changmaidman.scarlet.router.SentimentRouter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class LikeService extends SentimentService {

    private Map<Integer, User> likesMap;

    public LikeService() {
        this.likesMap = new HashMap<>();
    }

    @Override
    public void processSentiment(SentimentService service) {

        List<SentimentRegistry> sentimentRegistryList =
                LikeRouter.INSTANCE.getSentimentRegistry();

        sentimentRegistryList
                .forEach(sentimentRegistry ->
                        CompletableFuture.runAsync(() ->
                                invokeSentimentRegistry(sentimentRegistry)));
    }
}
