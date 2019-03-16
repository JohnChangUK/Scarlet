package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.exception.UserDoesNotExistException;
import com.changmaidman.scarlet.model.Match;
import com.changmaidman.scarlet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.changmaidman.scarlet.util.Utils.USER_DOES_NOT_EXIST;

@Service
public class UserService {

    private final List<User> usersList;

    @Autowired
    public UserService(List<User> usersList) {
        this.usersList = usersList;
    }

    public List<User> getAllUsers() {
        return usersList;
    }

    public List<MultipartFile> getUserPhotos(String id) {
        return usersList.stream()
                .filter(getUserByIdStream(id))
                .flatMap(this::getUserPhotosStream)
                .collect(Collectors.toList());
    }

    public List<Match> getUserMatches(String id) {
        return usersList.stream()
                .filter(getUserByIdStream(id))
                .flatMap(this::getUserMatchesStream)
                .collect(Collectors.toList());
    }

    public User getUser(String id) throws UserDoesNotExistException {
        Optional<User> user = usersList.stream()
                .filter(getUserByIdStream(id))
                .findFirst();

        if (user.isPresent()) {
            return user.get();
        }

        throw new UserDoesNotExistException(USER_DOES_NOT_EXIST);
    }

    private Stream<Match> getUserMatchesStream(User user) {
        return user.getMatches().stream();
    }

    private Stream<MultipartFile> getUserPhotosStream(User user) {
        return user.getPhotos().stream();
    }

    private Predicate<User> getUserByIdStream(String id) {
        return user -> id.equals(user.getId());
    }
}
