package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.exception.UserDoesNotExistException;
import com.changmaidman.scarlet.model.Match;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.model.Users;
import com.changmaidman.scarlet.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.changmaidman.scarlet.util.Utils.USER_DOES_NOT_EXIST;

@Service
public class UserService {

    private final List<User> usersList;

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(List<User> usersList, UsersRepository usersRepository) {
        this.usersList = usersList;
        this.usersRepository = usersRepository;
    }

    public List<User> getAllUser() {
//        return usersList;
        Map<User, String> map = new HashMap<>();
        List<Map.Entry<User, String>> usersList = new ArrayList<>(map.entrySet());
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(
                        Comparator.comparing(User::getId)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
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

    public List<Users> getUserByName(String name) {
        return usersRepository.findByName(name);
    }

    public Users getUsersById(Integer id) throws UserDoesNotExistException {
        Optional<Users> user = usersRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }

        throw new UserDoesNotExistException(USER_DOES_NOT_EXIST);
    }

    public Users updateUserById(Integer id, String name) throws UserDoesNotExistException {
        Optional<Users> user = usersRepository.findById(id);
        if (user.isPresent()) {
            user.get().setName(name);
            return usersRepository.save(user.get());
        }

        throw new UserDoesNotExistException(USER_DOES_NOT_EXIST);
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
