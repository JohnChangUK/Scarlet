package com.changmaidman.scarlet.util;

import com.changmaidman.scarlet.model.Match;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.model.Users;

import java.util.List;

public class Utils {

    public static final String USER_DOES_NOT_EXIST = "User does not exist, " +
            "please sign up to Scarlet first!";

    public static User createUser(String id, String name, List<Match> matches) {
        return User.builder()
                .id(id)
                .name(name)
                .matches(matches)
                .build();
    }

    public static Match createMatch(String id, Users users) {
        return Match.builder()
                .id(id)
                .users(users)
                .build();
    }
}
