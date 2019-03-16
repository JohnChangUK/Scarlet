package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LikeService {

    Map<Integer, User> likesMap;
}
