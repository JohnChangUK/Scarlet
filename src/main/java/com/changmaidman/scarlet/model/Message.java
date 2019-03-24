package com.changmaidman.scarlet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Builder
@Data
//@Entity
@Table(name = "message", catalog = "test")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String to;
    private String from;
    private String content;
    @Column(name = "time_stamp")
    private long timestamp;
}