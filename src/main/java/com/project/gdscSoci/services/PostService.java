package com.project.gdscSoci.services;

import com.project.gdscSoci.Dto.PostCreateRequest;
import com.project.gdscSoci.Dto.PostUpdateRequest;
import com.project.gdscSoci.entities.Post;
import com.project.gdscSoci.entities.User;
import com.project.gdscSoci.repos.PostRepository;
import com.project.gdscSoci.responses.LikeResponse;
import com.project.gdscSoci.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private LikeService likeService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Autowired
    public void setLikeService(@Lazy LikeService likeService){
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent()){
            list = postRepository.findByUserId(userId.get());
        }else{
          list =   postRepository.findAll();
        }
        return list.stream().map(p-> {
          List<LikeResponse> likes=likeService.getAllLikesWithParam(Optional.ofNullable(null),Optional.of(p.getId()));
            return new PostResponse(p,likes);
        }).collect(Collectors.toList());
    }

    public List<Post> getOneUserPosts(Long userId) {
        return postRepository.findByUserId(userId);
    }


    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = userService.getOneUserById(newPostRequest.getUserId());
        if(user == null){
            return null;
        }else {
            Post toSave = new Post();
            toSave.setId(newPostRequest.getId());
            toSave.setText(newPostRequest.getText());
            toSave.setTitle(newPostRequest.getTitle());
            toSave.setCreatedAt(LocalDateTime.now());
            toSave.setUser(user);
            return postRepository.save(toSave);
        }

    }

    public Post updateOnePostById(Long postId , PostUpdateRequest updatePost) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post toUpdate = post.get();
            toUpdate.setTitle(updatePost.getTitle());
            toUpdate.setText(updatePost.getText());
            toUpdate.setUpdatedAt(LocalDateTime.now());
            postRepository.save(toUpdate);
            return toUpdate;
        }else{
            return null;
        }

    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
