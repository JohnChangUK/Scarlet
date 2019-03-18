package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.annotation.DislikeHandler;
import com.changmaidman.scarlet.model.Dislike;

import java.util.ArrayList;
import java.util.HashMap;

public class DislikeRouter extends SentimentRouter {

    public static final DislikeRouter INSTANCE = new DislikeRouter();

    private DislikeRouter() {
        this.sentimentRegistry = new HashMap<>();
        this.methodList = new ArrayList<>();
        this.registry = new HashMap<>();
        process(DislikeHandler.class, Dislike.class);
    }
}
