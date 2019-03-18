package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.annotation.LikeHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class LikeRouter extends SentimentRouter {

    public static final LikeRouter INSTANCE = new LikeRouter();

    private LikeRouter() {
        this.methodList = new ArrayList<>();
        this.sentimentRegistry = new HashMap<>();
        process(LikeHandler.class);
    }
}
