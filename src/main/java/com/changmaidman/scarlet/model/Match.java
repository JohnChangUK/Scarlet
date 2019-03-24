package com.changmaidman.scarlet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Builder
@Data
//@Entity
@Table(name = "match", catalog = "test")
public class Match {

    @Id
    private String id;
    private Users users;
//    private List<Message> messages;

    public Match() {
    }
}
