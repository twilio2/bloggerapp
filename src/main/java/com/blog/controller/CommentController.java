package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.payload.PostDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
@PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam("postId") long postId, @RequestBody
    CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("comment is deleted!!",HttpStatus.OK);

    }
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable long postId){
        List<CommentDto> commentDto = commentService.getCommentByPostId(postId);
     return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(){
       List<CommentDto> commentDtos= commentService.getAllComments();
       return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,@PathVariable("Id") long commentId,@RequestBody CommentDto commentDto){
        CommentDto Dto = commentService.updateComment(postId,commentId, commentDto);
        return new ResponseEntity<>(Dto,HttpStatus.OK);
    }
}
//    @PutMapping
//    public ResponseEntity<PostDto> updatePost(
//            @RequestParam("PostId") Long postId,
//            @RequestBody PostDto postDto
//    ) {
//        PostDto dto = postService.updatePost(postId, postDto);
//        return new ResponseEntity<>(dto,HttpStatus.OK);
