package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Blogs;

public interface BlogsDAO extends JpaRepository<Blogs, Integer>{

}
