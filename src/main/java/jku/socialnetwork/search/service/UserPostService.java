package jku.socialnetwork.search.service;

import jku.socialnetwork.search.exception.badRequest.BadRequestException;
import jku.socialnetwork.search.exception.matchNotFound.MatchNotFoundException;
import jku.socialnetwork.search.model.Post;
import jku.socialnetwork.search.model.UserPost;
import jku.socialnetwork.search.repository.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserPostService {
    private final UserPostRepository userPostRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserPostService(UserPostRepository userPostRepository, MongoTemplate mongoTemplate) {
        this.userPostRepository = userPostRepository;
        this.mongoTemplate = mongoTemplate;
    }

    // searches for matching posts with the 'search'-value
    // the 'username' is the current user that is using the search-service
    // all post who are created from the user with the given username do not get returned
    public Map<String, Post> findPostWithMatch(String username, String search) throws MatchNotFoundException, BadRequestException {
        // check if search value is empty
        if (search.isEmpty()) {
            throw new BadRequestException("Bad request!");
        }
        List<UserPost> userPostList = userPostRepository.findAll();
        Map<String, Post> response = new HashMap<>();
        // convert search value to lower case
        String searchInLowerCase = search.toLowerCase();

        // search for matching posts
        for (UserPost userPost : userPostList) {
            for (Post post : userPost.getPostList()) {
                // check if the post matches the search value and ignores the post if it is a post from the current user who is using this service
                if (post.getMessage().toLowerCase().contains(searchInLowerCase) && !userPost.getUsername().equals(username)) {
                    response.put(userPost.getUsername(), post);
                }
            }
        }
        // throws exception if no matching posts get found
        if (response.isEmpty()) {
            throw new MatchNotFoundException("No match found!");
        }
        return response;
    }
}
