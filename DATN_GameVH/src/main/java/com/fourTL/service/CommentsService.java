package com.fourTL.service;

import com.fourTL.DTO.CommentDTO1;
import com.fourTL.entities.Comment;


import java.util.List;

public interface CommentsService {
    List<Comment> findAll();

   // List<Comment> findAllByIdBlog(Integer id);

    //Comment findById(Integer id);
    List<Comment> findAllCommentAndBlogByIdBlog(Integer blogId);

    Comment save(CommentDTO1 dto);
    //Comment createCMT(Comment comment);

}
