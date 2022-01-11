package jku.socialnetwork.search.controller;

import jku.socialnetwork.search.exception.badRequest.BadRequestException;
import jku.socialnetwork.search.exception.matchNotFound.MatchNotFoundException;
import jku.socialnetwork.search.model.User;
import jku.socialnetwork.search.service.UserService;
import jku.socialnetwork.search.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/searchUser")
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/searchForMatch/{search}")
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
