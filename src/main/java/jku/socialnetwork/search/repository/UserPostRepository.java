package jku.socialnetwork.search.repository;

import jku.socialnetwork.search.model.UserPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostRepository extends MongoRepository<UserPost, String> {

}
