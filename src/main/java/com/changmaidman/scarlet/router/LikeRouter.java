package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.annotation.LikeHandler;
import com.changmaidman.scarlet.model.Like;

import java.util.ArrayList;
import java.util.HashMap;

public class LikeRouter extends SentimentRouter {

    public static final LikeRouter INSTANCE = new LikeRouter();

    private LikeRouter() {
        this.sentimentRegistry = new HashMap<>();
        this.methodList = new ArrayList<>();
        this.registry = new HashMap<>();
        process(LikeHandler.class, Like.class);
    }
}
