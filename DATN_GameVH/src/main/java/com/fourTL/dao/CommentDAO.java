package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Comment;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentDAO extends JpaRepository<Comment, Integer>{
    @Query(value = "SELECT c FROM Comments c WHERE c.IdBlog= ?1 order by c.ThoiGianCmt desc ",nativeQuery = true)
    List<Comment> findAllByIdBlog(Integer id);
}
