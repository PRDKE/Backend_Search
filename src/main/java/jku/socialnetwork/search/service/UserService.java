package jku.socialnetwork.search.service;

import jku.socialnetwork.search.exception.badRequest.BadRequestException;
import jku.socialnetwork.search.exception.matchNotFound.MatchNotFoundException;
import jku.socialnetwork.search.model.User;
import jku.socialnetwork.search.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUserWithMatch(String username, String search) throws MatchNotFoundException, BadRequestException {
        if (search.isEmpty()) {
            throw new BadRequestException("Bad request!");
        }
        List<User> userList = userRepository.findAll();
        List<User> response = new ArrayList<>();
        String searchInLowerCase = search.toLowerCase();
        for (User user : userList) {
            if ((user.getUsername().toLowerCase().contains(searchInLowerCase) || user.getUsername().toLowerCase().equals(searchInLowerCase)) && !user.getUsername().equals(username)) {
                response.add(user);
            }
        }
        if (response.isEmpty()) {
            throw new MatchNotFoundException("No match found!");
        }
        return response;
    }
}
