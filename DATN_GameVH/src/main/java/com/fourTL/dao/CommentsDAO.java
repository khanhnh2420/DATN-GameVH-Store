package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Comments;

public interface CommentsDAO extends JpaRepository<Comments, Integer>{

}
