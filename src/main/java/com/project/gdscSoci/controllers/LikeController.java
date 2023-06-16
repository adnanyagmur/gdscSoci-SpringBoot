package com.project.gdscSoci.controllers;

import com.project.gdscSoci.Dto.LikeCreateRequest;
import com.project.gdscSoci.entities.Like;
import com.project.gdscSoci.responses.LikeResponse;
import com.project.gdscSoci.services.LikeService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/getAllLikes")
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId,
                                          @RequestParam Optional<Long> postId){
        return likeService.getAllLikesWithParam(userId,postId);
    }

    @PostMapping("/createOneLike")
    public Like createOneLike(@RequestBody LikeCreateRequest request){
        return likeService.createOneLike(request);
    }

    @GetMapping("/getOneLike/{likeId}")
    public Like getOneLike(@PathVariable Long likeId) {
        return likeService.getOneLikeById(likeId);
    }

    @Transactional
    @DeleteMapping("/{userId}/{postId}")
    public void deleteOneLike(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        likeService.deleteOneLikeById(userId, postId);
    }
}
