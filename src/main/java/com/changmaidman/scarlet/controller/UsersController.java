package com.changmaidman.scarlet.controller;

import java.util.List;

import com.changmaidman.scarlet.exception.UserDoesNotExistException;
import com.changmaidman.scarlet.model.Match;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.model.Users;
import com.changmaidman.scarlet.service.SentimentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.changmaidman.scarlet.service.UserService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class UsersController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    private final UserService userService;
    private final SentimentService likeService;
    private final SentimentService superLikeService;
    private final SentimentService dislikeService;

    @Autowired
    public UsersController(UserService userService,
                           SentimentService likeService,
                           SentimentService superLikeService,
                           SentimentService dislikeService) {
        this.userService = userService;
        this.likeService = likeService;
        this.superLikeService = superLikeService;
        this.dislikeService = dislikeService;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping(value = "/allUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsersByName(@PathVariable(value = "name") String name) {
        return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
    }

    @GetMapping(value = "/users/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> getUserById(@PathVariable(value = "id") Integer id) {
        try {
            return new ResponseEntity<>(userService.getUsersById(id), HttpStatus.OK);
        } catch (UserDoesNotExistException e) {
            log.error("Error: ", e);
        }

        return new ResponseEntity<>(new Users(), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/users/update/{id}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> updateUserById(@PathVariable("id") Integer id,
                                                @PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(userService.updateUserById(id, name), HttpStatus.OK);
        } catch (UserDoesNotExistException e) {
            log.error("Error: ", e);
        }

        return new ResponseEntity<>(new Users(), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(@PathVariable(value = "id") String id) {

        try {
            return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
        } catch (UserDoesNotExistException e) {
            log.error("Error: ", e);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("User does not exist");
    }

    @GetMapping(value = "/user/{id}/photos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MultipartFile>> getUserPhotos(
            @PathVariable(value = "id") String id) {

        return new ResponseEntity<>(userService.getUserPhotos(id), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}/matches", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Match>> getUserMatches(
            @PathVariable(value = "id") String id) {

        return new ResponseEntity<>(userService.getUserMatches(id), HttpStatus.OK);
    }

    @PostMapping(value = "/user/{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Match>> likeUser(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "like") String otherUserId) {

        likeService.processSentiment(likeService);
        return new ResponseEntity<>(userService.getUserMatches(id), HttpStatus.OK);
    }

    @PostMapping(value = "/user/{id}/superLike", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Match>> superLikeUser(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "superLike") String otherUserId) {

        superLikeService.processSentiment(superLikeService);
        return new ResponseEntity<>(userService.getUserMatches(id), HttpStatus.OK);
    }

    @PostMapping(value = "/user/{id}/dislike", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> dislikeUser(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "dislike") String otherUserId) throws UserDoesNotExistException {

        dislikeService.processSentiment(dislikeService);
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }
}
