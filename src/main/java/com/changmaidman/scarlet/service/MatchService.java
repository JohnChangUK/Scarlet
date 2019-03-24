package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.exception.UserDoesNotExistException;
import com.changmaidman.scarlet.model.Match;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.model.Users;
import com.changmaidman.scarlet.repository.UsersRepository;
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
public class MatchService {

    private final List<User> usersList;

//    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(List<User> usersList) {
        this.usersList = usersList;
//        this.matchRepository = matchRepository;
    }

//    public List<Match> getAllMatches() {
//        return matchRepository.findAll();
//    }

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
