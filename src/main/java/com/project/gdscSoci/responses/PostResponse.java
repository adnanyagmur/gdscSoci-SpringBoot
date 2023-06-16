package com.project.gdscSoci.responses;

import com.project.gdscSoci.entities.Post;
import com.project.gdscSoci.entities.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {

    Long id;
    Long userId;
    String userName;
    String title;
    String text;
    List<LikeResponse> postLikes;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public PostResponse (Post entity, List<LikeResponse> likes) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getUserName();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.postLikes = likes;
    }

}
