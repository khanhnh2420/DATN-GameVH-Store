package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentDAO extends JpaRepository<Comment, Integer>{
    @Query(value = "SELECT c.Content, b.id AS blogId FROM Comment c INNER JOIN Blog b ON c.BlogId = b.id WHERE b.id = :blogId ORDER BY c.CreateDate DESC ",nativeQuery = true)
    List<Comment> findAllCommentAndBlogByIdBlog(@Param("blogId") Integer blogId);
   // List<Comment> findAllByIdBlog(Integer id);
}
