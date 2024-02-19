package com.blog.serviceImpl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post  not found with the id::" + postId)
        );
        Comment comment = maptoEntity(commentDto);   // child objects
        comment.setPosts(post);                     // child object.set (parent object)
        Comment savedComment = commentRepository.save(comment); // save child object
        CommentDto dto = maptoDto(savedComment);
        return dto;


    }

     Comment maptoEntity (CommentDto commentDto){
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
     }




    CommentDto maptoDto (Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    @Override
    public void deleteComment(long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment  nto found with this id :: " + commentId)
        );
        commentRepository.deleteById( commentId);




    }

    @Override
    public List<CommentDto> getAllComment() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDtos = comments.stream().map(p -> maptoDto(p)).collect(Collectors.toList());

        return commentDtos;
    }

    @Override
    public CommentDto updateComment(long commentId, CommentDto commentDto) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment  nto found with this id :: " + commentId)
        );

       comment.setName(commentDto.getName());
       comment.setEmail(commentDto.getEmail());
       comment.setBody(commentDto.getBody());

        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = maptoDto(savedComment);




        return dto;
    }
}
