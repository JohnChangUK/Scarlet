package com.changmaidman.scarlet.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Builder
@Data
public class User {

    private String id;
    private String name;
    private String dateOfBirth;
    private String bio;
    private List<MultipartFile> photos;
    private List<Match> matches;

    public void addMatch(Match match) {
        matches.add(match);
    }

    private void addPhoto(MultipartFile photo) {
        photos.add(photo);
    }
}
