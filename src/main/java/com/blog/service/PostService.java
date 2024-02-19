package com.blog.service;

import com.blog.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    void deletePostByPostId(long postId);

    PostDto updatePostByPostId(long postId,PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(long postId);

    List<PostDto> getAllPostsPagenation(int pageNo, int pageSize, String sortBy, String sortDir);
}
