package com.changmaidman.scarlet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users", catalog = "test")
public class Users {

    @Id
    private String id;
    private String name;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column(name = "biography")
    private String bio;
//    private MultipartFile photos;
//    private Match matches;

    public Users() {
    }
}
