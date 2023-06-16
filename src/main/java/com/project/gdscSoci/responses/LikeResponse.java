package com.project.gdscSoci.responses;

import com.project.gdscSoci.entities.Like;
import com.project.gdscSoci.entities.Post;
import com.project.gdscSoci.entities.User;
import lombok.Data;

@Data
public class LikeResponse {

    Long id;
    Long userId;
    Long postId;

    public LikeResponse(Like entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.postId = entity.getPost().getId();
    }
}
