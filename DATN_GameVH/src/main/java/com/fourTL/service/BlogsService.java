package com.fourTL.service;

import com.fourTL.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogsService {
List<Blog> findAll();
Blog findById(Integer id);
List<Blog> findAllByOrderByCreateDateDesc();

List<Blog> getListLastestBlogs();
    List<Blog> getListHighestComments();

    Page<Blog> getlistBlogsbyComments(String id , int maxcoment, Pageable pageable);

    Blog getBlogbyTittleSearch(String tittleSearch);
}
