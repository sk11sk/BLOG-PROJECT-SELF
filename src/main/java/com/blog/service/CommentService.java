package com.blog.service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

   void deleteComment(long commentId);

    List<CommentDto> getAllComment();

    CommentDto updateComment(long commentId, CommentDto commentDto);
}
