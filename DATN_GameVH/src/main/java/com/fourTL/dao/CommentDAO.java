package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Comment;

public interface CommentDAO extends JpaRepository<Comment, Integer>{

}
