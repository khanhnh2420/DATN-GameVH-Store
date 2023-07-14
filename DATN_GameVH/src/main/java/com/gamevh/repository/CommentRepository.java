package com.gamevh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamevh.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    //@Query(value = "SELECT c.Content, b.Id AS blogId FROM Comment c INNER JOIN Blog b ON c.BlogId = b.Id WHERE b.Id = :blogId ORDER BY c.CreateDate DESC ", nativeQuery = true)

    @Query(value = "SELECT c.Content, b.id AS blogId FROM Comment c INNER JOIN Blog b ON c.BlogId = b.id WHERE b.id = :blogId ORDER BY c.CreateDate DESC ",nativeQuery = true)
    List<Comment> findAllCommentAndBlogByIdBlog(@Param("blogId") Integer blogId);
   // List<Comment> findAllByIdBlog(Integer id);

    List<Comment> findByBlogId(Integer blogId);
}
