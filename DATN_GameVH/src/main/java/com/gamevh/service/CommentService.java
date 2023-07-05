package com.gamevh.service;

import java.util.List;

<<<<<<< HEAD:DATN_GameVH/src/main/java/com/fourTL/service/CommentsService.java
import com.fourTL.DTO.CommentDTO1;
import com.fourTL.entities.Comment;
=======
import com.gamevh.entities.Comment;
>>>>>>> origin/develop:DATN_GameVH/src/main/java/com/gamevh/service/CommentService.java

public interface CommentService {
    List<Comment> findAll();

    // List<Comment> findAllByIdBlog(Integer id);

    //Comment findById(Integer id);
    List<Comment> findAllCommentAndBlogByIdBlog(Integer blogId);

    Comment save(CommentDTO1 dto);
    //Comment createCMT(Comment comment);

}
