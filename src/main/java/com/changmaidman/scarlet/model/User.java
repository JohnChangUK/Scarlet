package com.changmaidman.scarlet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Component
public class User {

    private String id;
    private String name;
    private String dateOfBirth;
    private String bio;
    private List<MultipartFile> photos = new ArrayList<>();
    private List<Match> matches = new ArrayList<>();

    @Autowired
    public User(List<Match> matches) {
        this.matches = matches;
        matches.add(Match.builder().user(User.builder().build()).build());
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    private void addPhoto(MultipartFile photo) {
        photos.add(photo);
    }
}
