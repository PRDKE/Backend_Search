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

    public Map<String, Post> findPostWithMatch(String search) throws MatchNotFoundException, BadRequestException {
        if (search.isEmpty()) {
            throw new BadRequestException("Bad request!");
        }
        List<UserPost> userPostList = userPostRepository.findAll();
        Map<String, Post> response = new HashMap<>();
        String searchInLowerCase = search.toLowerCase();

        for (UserPost userPost : userPostList) {
            for (Post post : userPost.getPostList()) {
                if (post.getMessage().toLowerCase().contains(searchInLowerCase)) {
                    response.put(userPost.getUsername(), post);
                }
            }
        }
        if (response.isEmpty()) {
            throw new MatchNotFoundException("No match found!");
        }
        return response;
    }
}
