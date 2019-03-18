package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.annotation.DislikeHandler;
import com.changmaidman.scarlet.annotation.LikeHandler;
import com.changmaidman.scarlet.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LikeService {

    Map<Integer, User> likesMap;

    @LikeHandler
    public void process() {
        System.out.println("Like handler");
    }

    @LikeHandler
    public void process2() {
        System.out.println("Like handler2");
    }

    @LikeHandler
    public void process3() {
        System.out.println("Like handler3");
    }

    @DislikeHandler
    public void processDislike() {
        System.out.println("Dislike handler");
    }

    @DislikeHandler
    public void processDislike2() {
        System.out.println("Dislike handler2");
    }

    @DislikeHandler
    public void processDislike3() {
        System.out.println("Dislike handler3");
    }
}
