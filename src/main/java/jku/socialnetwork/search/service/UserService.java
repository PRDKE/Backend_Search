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

    // search for a matching username
    // the given username is the current user that is using the search service
    public List<User> findUserWithMatch(String username, String search) throws MatchNotFoundException, BadRequestException {
        // check if search value is empty
        if (search.isEmpty()) {
            throw new BadRequestException("Bad request!");
        }
        List<User> userList = userRepository.findAll();
        List<User> response = new ArrayList<>();
        // convert search value to lower case
        String searchInLowerCase = search.toLowerCase();
        // search for matching usernames
        for (User user : userList) {
            // check if the username matches the search value and ignores the username if it matches the current user who is using this service
            if ((user.getUsername().toLowerCase().contains(searchInLowerCase) || user.getUsername().toLowerCase().equals(searchInLowerCase)) && !user.getUsername().equals(username)) {
                response.add(user);
            }
        }
        // throws exception if no matching username gets found
        if (response.isEmpty()) {
            throw new MatchNotFoundException("No match found!");
        }
        return response;
    }
}
