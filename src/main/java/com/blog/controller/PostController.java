package com.blog.controller;


import com.blog.payload.PostDto;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    //http://localhost:8080/api/post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

//http://localhost:8080/api/post/postId
//    @DeleteMapping("/{postId}")
//    public ResponseEntity<String> deletePost(@PathVariable long postId){
//        postService.deletePostByPostId(postId);
//        return new ResponseEntity<>("Post is deleted by the PostId ::"+ postId, HttpStatus.OK);
//    }


   //http://localhost:8080/api/post?postId=postId
   @DeleteMapping
    public ResponseEntity<String> deletePost( @RequestParam ("postId") long postId){ // or ( @RequestParam long postId)  is correct for the same url
        postService.deletePostByPostId(postId);                                             //( @RequestParam (name=("postId")) long postId) is correct for the same url
        return new ResponseEntity<>("Post is deleted by the PostId ::"+ postId, HttpStatus.OK);
    }

    //http://localhost:8080/api/post/postId
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long postId , @RequestBody PostDto  postDto){

        PostDto dto = postService.updatePostByPostId(postId, postDto);
        return new ResponseEntity<>( dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/post             // get all posts
//    @GetMapping
//    public ResponseEntity<List<PostDto>>  getAllPosts(){
//        List<PostDto> allPosts = postService.getAllPosts();
//        return new ResponseEntity<>(allPosts, HttpStatus.OK);
//    }

//http://localhost:8080/api/post/postId
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto>  getPost(@PathVariable  long postId ){
        PostDto dto = postService.getPostById(postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //http://localhost:8080/api/post?postId=postId
//    @GetMapping                                           /// this is not working bez there is already  a  @GetMapping  present (get all posts )
                                                            // {GET [/api/post]}: There is already 'postController' bean method
//    public ResponseEntity<PostDto>  getPost(@RequestParam long postId ){  // java.lang.IllegalStateException: Ambiguous mapping. Cannot map 'postController' method
//        PostDto dto = postService.getPostById(postId);
//        return new ResponseEntity<>(dto, HttpStatus.OK);
//    }





//http://localhost:8080/api/post?pageNo=1&pageSize=5 // it will not take any  of  the default values cz pageno and page size is provided
                                                        // manually in the url
//http://localhost:8080/api/post?pageSize=5  // page no will will take the default value  for this url as page no is not provided in url
//http://localhost:8080/api/post?pageNo=1    // page size will take the default value  for this url as page size is not provided in url


//@GetMapping("/pagination") //http://localhost:8080/api/post/pagenation : url


//http://localhost:8080/api/post?pageNo=1&pageSize=5&sortBy=title&sortDir=desc
@PreAuthorize("hasRole('ADMIN')")
@GetMapping
public ResponseEntity<List<PostDto>>  getAllPosts(
        @RequestParam(name="pageNo",defaultValue = "0",required= false)  int pageNo ,
        @RequestParam( name = "pageSize", defaultValue = "3",required = false) int pageSize,
        @RequestParam( name = "sortBy", defaultValue = "id",required = false) String sortBy,    // sortBy:id or title or content or description
        @RequestParam( name = "sortDir", defaultValue = "id",required = false) String sortDir   //sortDir :asc or desc
){
    List<PostDto> allPosts = postService.getAllPostsPagenation(pageNo,pageSize,sortBy,sortDir);
    return new ResponseEntity<>(allPosts, HttpStatus.OK);
}



}
