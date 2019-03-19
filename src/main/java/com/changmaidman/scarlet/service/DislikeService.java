package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.model.SentimentRegistry;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.router.DislikeRouter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class DislikeService extends SentimentService {

    private Map<Integer, User> dislikesMap;

    public DislikeService() {
        this.dislikesMap = new HashMap<>();
    }

    @Override
    public void processSentiment(SentimentService service) {

        List<SentimentRegistry> sentimentRegistryList =
                DislikeRouter.INSTANCE.getSentimentRegistry();

        sentimentRegistryList
                .forEach(sentimentRegistry ->
                        CompletableFuture.runAsync(() ->
                                invokeSentimentRegistry(sentimentRegistry)));
    }
}
