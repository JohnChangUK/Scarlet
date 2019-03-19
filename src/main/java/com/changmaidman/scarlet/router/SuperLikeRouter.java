package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.annotation.SuperLikeHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class SuperLikeRouter extends SentimentRouter {

    public static final SuperLikeRouter INSTANCE = new SuperLikeRouter();

    private SuperLikeRouter() {
        this.methodList = new ArrayList<>();
        this.sentimentRegistry = new HashMap<>();
        process(SuperLikeHandler.class);
    }
}
