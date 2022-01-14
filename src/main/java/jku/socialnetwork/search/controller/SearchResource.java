package jku.socialnetwork.search.controller;

import jku.socialnetwork.search.exception.badRequest.BadRequestException;
import jku.socialnetwork.search.exception.matchNotFound.MatchNotFoundException;
import jku.socialnetwork.search.model.Post;
import jku.socialnetwork.search.model.User;
import jku.socialnetwork.search.service.UserPostService;
import jku.socialnetwork.search.service.UserService;
import jku.socialnetwork.search.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchResource {

    private final UserPostService userPostService;
    private final UserService userService;

    @Autowired
    public SearchResource(UserPostService userPostService, UserService userService) {
        this.userPostService = userPostService;
        this.userService = userService;
    }

    @GetMapping("/searchUserPostForMatch/{search}")
    public ResponseEntity<Map<String, Post>> findPostWithMatch(HttpServletRequest request, @PathVariable String search) throws MatchNotFoundException, BadRequestException {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = JwtUtils.getUsernameFromJwtToken(jwtToken);
        Map<String, Post> searchResult = this.userPostService.findPostWithMatch(username, search);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    @GetMapping("/searchUserForMatch/{search}")
    public ResponseEntity<List<User>> findUserWithMatch(HttpServletRequest request, @PathVariable String search) throws MatchNotFoundException, BadRequestException {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = JwtUtils.getUsernameFromJwtToken(jwtToken);
        List<User> userList = userService.findUserWithMatch(username, search);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
