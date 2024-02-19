package com.blog.controller;


import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //http://localhost:8080/api/comments/14
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @RequestBody CommentDto commentDto  ){
        CommentDto dto= commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId  ){
            commentService.deleteComment(commentId);
        return new ResponseEntity<>("comment is deleted with the id ::"+commentId, HttpStatus.OK);
    }

    //http://localhost:8080/api/comments
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComment( ){
        List<CommentDto> dto= commentService.getAllComment();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long commentId, @RequestBody CommentDto commentDto  ){
        CommentDto dto= commentService.updateComment(commentId,commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
