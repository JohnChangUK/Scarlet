package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.exception.UserDoesNotExistException;
import com.changmaidman.scarlet.model.Match;
import com.changmaidman.scarlet.model.Users;
import com.changmaidman.scarlet.model.UsersContact;
import com.changmaidman.scarlet.repository.UsersContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsersContactService {

    private final UsersContactRepository usersContactRepository;
    private final UserService userService;

    @Autowired
    public UsersContactService(UsersContactRepository usersContactRepository,
                               UserService userService) {
        this.usersContactRepository = usersContactRepository;
        this.userService = userService;
    }

    public List<UsersContact> getAllUsersContact() {
        return usersContactRepository.findAll();
    }

    public List<UsersContact> createUsersContact(Integer id, String phoneNumber)
            throws UserDoesNotExistException {
        Users user = userService.getUsersById(id);

        Match match = Match.builder()
                .timeStamp(Timestamp.valueOf(LocalDateTime.now()))
                .matchingUserId(75)
                .build();

        Match match2 = Match.builder()
                .timeStamp(Timestamp.valueOf(LocalDateTime.now()))
                .matchingUserId(55)
                .build();

        List<Match> matches = new ArrayList<>(Arrays.asList(match, match2));
        user.setMatches(matches);

        UsersContact usersContact = UsersContact.builder()
                .users(user)
                .phoneNumber(phoneNumber)
                .build();

        usersContactRepository.save(usersContact);
        return usersContactRepository.findAll();
    }
}
