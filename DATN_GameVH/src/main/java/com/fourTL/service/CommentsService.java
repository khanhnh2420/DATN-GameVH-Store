package com.fourTL.service;

import java.util.List;

import com.fourTL.DTO.CommentDTO1;
import com.fourTL.entities.Comment;

public interface CommentsService {
    List<Comment> findAll();

    // List<Comment> findAllByIdBlog(Integer id);

    //Comment findById(Integer id);
    List<Comment> findAllCommentAndBlogByIdBlog(Integer blogId);

    Comment save(CommentDTO1 dto);
    //Comment createCMT(Comment comment);

}
