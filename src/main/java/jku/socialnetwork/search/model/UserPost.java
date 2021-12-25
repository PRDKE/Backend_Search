package jku.socialnetwork.search.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class UserPost {
    @Id
    private String id;
    private String username;
    private List<Post> postList;

    public UserPost() { }

    public UserPost(String username) {
        this.username = username;
        this.postList = new ArrayList<>();
    }
}
