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
import java.sql.Timestamp;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "matches", catalog = "test")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "time_stamp")
    private Timestamp timeStamp;
    @Column(name = "matching_user_id")
    private Integer matchingUserId;
//    private Users users;
//    private List<Message> messages;

    public Match() {
    }
}
