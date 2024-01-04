package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id " + postId)
        );
        Comment comment =new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setBody(savedComment.getBody());
        dto.setEmail(savedComment.getEmail());

        return dto;
    }

    @Override
    public void deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new com.blog.exception.ResourceNotFoundException("comment not found with Id " + commentId)
        );
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentsDtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return  commentsDtos;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(postId ).orElseThrow(
                () -> new com.blog.exception.ResourceNotFoundException("post not found with id: " + postId)
        );
      comment.setName(commentDto.getName());
      comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment savedComments = commentRepository.save(comment);
        CommentDto dto = mapToDto(savedComments);
        return dto;
    }


    CommentDto mapToDto(Comment comment){
            CommentDto dto = new CommentDto();
            dto.setId(comment.getId());
            dto.setName(comment.getName());
            dto.setBody(comment.getBody());
            dto.setEmail(comment.getEmail());
            return dto;

    }

}
//    Post post = postRepo.findById(postId).orElseThrow(
//            () -> new com.blog.exception.ResourceNotFoundException("post not found with id: "+postId)
//    );
//        post.setTitle(postDto.getTitle());
//                post.setContent(postDto.getContent());
//                post.setDescription(postDto.getDescription());
//                Post savedPost = postRepo.save(post);
//                PostDto dto = mapToDto(savedPost);
//
//                return dto;
//                }