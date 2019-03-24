package com.changmaidman.scarlet.controller;

import com.changmaidman.scarlet.exception.UserDoesNotExistException;
import com.changmaidman.scarlet.model.UsersContact;
import com.changmaidman.scarlet.service.UsersContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/usersContact")
public class UsersContactController {

    private static final Logger log = LoggerFactory.getLogger(UsersContactController.class);

    private final UsersContactService usersContactService;

    @Autowired
    public UsersContactController(UsersContactService usersContactService) {
        this.usersContactService = usersContactService;
    }

    @GetMapping
    public List<UsersContact> getAllUsersContact() {
        return usersContactService.getAllUsersContact();
    }

    @GetMapping("/{id}")
    public List<UsersContact> createUsersContact(@PathVariable("id") Integer id,
                                                 @RequestParam("phoneNumber") String phoneNumber) {
        try {
            return usersContactService.createUsersContact(id, phoneNumber);
        } catch (UserDoesNotExistException e) {
            log.error("Error: ", e);
        }

        return Collections.emptyList();
    }
}
