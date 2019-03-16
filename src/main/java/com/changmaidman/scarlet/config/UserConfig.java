package com.changmaidman.scarlet.config;

import com.changmaidman.scarlet.model.Match;
import com.changmaidman.scarlet.model.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static com.changmaidman.scarlet.util.Utils.*;

@Configuration
@EnableAutoConfiguration
public class UserConfig {

    @Bean
    public static List<User> usersList() {
        List<User> usersList = new ArrayList<>();
        usersList.add(createUser("1", "John", new ArrayList<>()));
        usersList.add(createUser("2", "Joe", new ArrayList<>()));
        usersList.add(createUser("3", "Solace", new ArrayList<>()));
        return usersList;
    }

    @Bean
    public List<Match> matches() {
        List<Match> matches = new ArrayList<>();
        matches.add(createMatch("11", createUser("6", "Julianna", new ArrayList<>())));
        matches.add(createMatch("12", createUser("7", "Kim", new ArrayList<>())));
        matches.add(createMatch("13", createUser("8", "Sally", new ArrayList<>())));
        return matches;
    }
}