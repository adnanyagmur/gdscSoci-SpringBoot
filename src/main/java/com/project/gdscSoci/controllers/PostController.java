package com.project.gdscSoci.controllers;

import com.project.gdscSoci.Dto.PostCreateRequest;
import com.project.gdscSoci.Dto.PostUpdateRequest;
import com.project.gdscSoci.entities.Post;
import com.project.gdscSoci.responses.PostResponse;
import com.project.gdscSoci.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //buraya user id gönderilirse tek 1 post da döenebilicek
    @GetMapping("/allPosts")
    public List<PostResponse> getAllPost(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }

    // yine de tekli bir servis de yazıyım

    @GetMapping("/oneUserPosts/{userId}")
    public List<Post> getOneUserPosts(@PathVariable Long userId){
        return postService.getOneUserPosts(userId);
    }

    @GetMapping("/getOnePost/{postId}")
    public Post getOnePost(@PathVariable Long postId){
        return postService.getOnePostById(postId);
    }

    @PostMapping("/createPost")
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest){
        return postService.createOnePost(newPostRequest);
    }

    @PutMapping("/updatePost/{postId}")
    public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePost){
        return postService.updateOnePostById(postId,updatePost);
    }

    @DeleteMapping("/deletePost/{postId}")
    public void deleteOnePost(@PathVariable Long postId){
        postService.deleteOnePostById(postId);
    }

}




