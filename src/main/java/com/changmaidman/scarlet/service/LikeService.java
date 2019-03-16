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
    public static void process() {
        System.out.println("Like handler");
    }

    @DislikeHandler
    public void processDislike() {
        System.out.println("Like handler");
    }
}
