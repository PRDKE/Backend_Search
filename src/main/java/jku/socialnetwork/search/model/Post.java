package jku.socialnetwork.search.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private int id;
    private String imageUrl;
    private String message;
    private LocalDateTime localDateTime;

    public Post (String imageUrl, String message) {
        this.id = 1;
        this.imageUrl = imageUrl;
        this.message = message;
        this.localDateTime = LocalDateTime.now();
    }
}
