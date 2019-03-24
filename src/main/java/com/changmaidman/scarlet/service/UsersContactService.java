package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.exception.UserDoesNotExistException;
import com.changmaidman.scarlet.model.Match;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.model.Users;
import com.changmaidman.scarlet.model.UsersContact;
import com.changmaidman.scarlet.repository.UsersContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        UsersContact usersContact = UsersContact.builder()
                .users(user)
                .phoneNumber(phoneNumber)
                .build();

        usersContactRepository.save(usersContact);
        return usersContactRepository.findAll();
    }
}
