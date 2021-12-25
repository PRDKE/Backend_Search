package jku.socialnetwork.search.controller;

import jku.socialnetwork.search.exception.badRequest.BadRequestException;
import jku.socialnetwork.search.exception.matchNotFound.MatchNotFoundException;
import jku.socialnetwork.search.model.Post;
import jku.socialnetwork.search.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/userPostSearch")
public class UserPostResource {
    private final UserPostService userPostService;

    @Autowired
    public UserPostResource(UserPostService userPostService) {
        this.userPostService = userPostService;
    }

    @GetMapping("/searchForMatch")
    public ResponseEntity<Map<String, Post>> findPostWithMatch(@RequestBody String search) throws MatchNotFoundException, BadRequestException {
        Map<String, Post> searchResult = this.userPostService.findPostWithMatch(search);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
