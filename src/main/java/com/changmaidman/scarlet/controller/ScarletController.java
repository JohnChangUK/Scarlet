package com.changmaidman.scarlet.controller;

import java.util.List;

import com.changmaidman.scarlet.exception.UserDoesNotExistException;
import com.changmaidman.scarlet.model.Match;
import com.changmaidman.scarlet.model.User;
import com.changmaidman.scarlet.service.SentimentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.changmaidman.scarlet.service.UserService;

@RestController
@RequestMapping("/")
public class ScarletController {

    private static final Logger log = LoggerFactory.getLogger(ScarletController.class);

    private final UserService userService;
    private final SentimentService likeService;
    private final SentimentService superLikeService;
    private final SentimentService dislikeService;

    @Autowired
    public ScarletController(UserService userService,
                             SentimentService likeService,
                             SentimentService superLikeService,
                             SentimentService dislikeService) {
        this.userService = userService;
        this.likeService = likeService;
        this.superLikeService = superLikeService;
        this.dislikeService = dislikeService;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
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
