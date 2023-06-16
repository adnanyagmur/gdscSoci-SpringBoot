package com.project.gdscSoci.services;

import com.project.gdscSoci.Dto.LikeCreateRequest;
import com.project.gdscSoci.entities.Like;
import com.project.gdscSoci.entities.Post;
import com.project.gdscSoci.entities.User;
import com.project.gdscSoci.repos.LikeRepository;
import com.project.gdscSoci.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;


    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId,
                                                   Optional<Long> postId) {
        List<Like> list;
        if (userId.isPresent() && postId.isPresent()){
            list = likeRepository.findByUserIdAndPostId(userId.get(),postId.get());
        }else if ( userId.isPresent()){
            list = likeRepository.findByUserId(userId.get());
        }else if ( postId.isPresent()){
            list = likeRepository.findByPostId(postId.get());
        }else {
            list = likeRepository.findAll();
        }
        //daha sonra bu like response olayına bak
        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like createOneLike(LikeCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());

        if(user != null && post !=null) {
            Like likeToSave = new Like();
            likeToSave.setId(request.getId());
            likeToSave.setPost(post);
            likeToSave.setUser(user);
            likeToSave.setCreatedAt(LocalDateTime.now());
            return likeRepository.save(likeToSave);
        }else{
            return null;
        }
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public void deleteOneLikeById(Long userId, Long postId) {
        likeRepository.deletePostByUserIdAndPostId(userId, postId);

    }
}
