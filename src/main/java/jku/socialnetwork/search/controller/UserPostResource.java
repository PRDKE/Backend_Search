package jku.socialnetwork.search.controller;

import jku.socialnetwork.search.exception.badRequest.BadRequestException;
import jku.socialnetwork.search.exception.matchNotFound.MatchNotFoundException;
import jku.socialnetwork.search.model.Post;
import jku.socialnetwork.search.service.UserPostService;
import jku.socialnetwork.search.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/searchUserPost")
public class UserPostResource {
    private final UserPostService userPostService;

    @Autowired
    public UserPostResource(UserPostService userPostService) {
        this.userPostService = userPostService;
    }

    @GetMapping("/searchForMatch/{search}")
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
}
