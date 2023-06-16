package com.project.gdscSoci.controllers;


import com.project.gdscSoci.Dto.CommentCreateRequest;
import com.project.gdscSoci.Dto.CommentUpdateRequest;
import com.project.gdscSoci.entities.Comment;
import com.project.gdscSoci.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getAllPostWithParam")
    public List<Comment> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return  commentService.getAllCommentsWithParam(userId,postId);
    }

    @GetMapping("/getOneComment/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneCommentById(commentId);
    }

    @PostMapping("/postOneComment")
    public Comment createOneComment(@RequestBody CommentCreateRequest request){
        return commentService.createOneCommentById(request);
    }

    @PutMapping("/updateOneComment/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request){
        return commentService.updateOneCommentById(commentId, request);
    }

    @DeleteMapping("/deleteOneComment/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId){
        commentService.deleteOneCommentById(commentId);
    }
}
