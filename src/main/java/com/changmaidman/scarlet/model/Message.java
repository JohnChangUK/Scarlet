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
@Table(name = "users", catalog = "test")
public class Message {

    @Id
    private String id;
    private String to;
    private String from;
    private String content;
    private long timestamp;
}