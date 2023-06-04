package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Blog;

public interface BlogDAO extends JpaRepository<Blog, Integer>{

}
