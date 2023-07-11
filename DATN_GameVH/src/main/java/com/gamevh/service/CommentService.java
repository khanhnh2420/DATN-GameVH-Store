package com.gamevh.service;

import java.util.List;


import com.gamevh.dto.CommentDTO1;
import com.gamevh.entities.Comment;


public interface CommentService {
    List<Comment> findAll();

    // List<Comment> findAllByIdBlog(Integer id);

    //Comment findById(Integer id);
    List<Comment> findAllCommentAndBlogByIdBlog(Integer blogId);

    Comment save(Comment dto);
    //Comment createCMT(Comment comment);

}
