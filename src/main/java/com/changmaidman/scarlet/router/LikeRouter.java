package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.annotation.LikeHandler;
import com.changmaidman.scarlet.model.Like;

import java.util.HashMap;

public class LikeRouter extends SentimentRouter {

    static final LikeRouter INSTANCE = new LikeRouter();

    private LikeRouter() {
        this.sentimentRegistry = new HashMap<>();
        process(LikeHandler.class, Like.class);
    }
}
