package com.fourTL.service;

import com.fourTL.entities.Blogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogsService {
List<Blogs> findAll();
Blogs findById(Integer id);
List<Blogs> findAllByOrderByCreateDateDesc();

List<Blogs> getListLastestBlogs();
    List<Blogs> getListHighestComments();

    Page<Blogs> getlistBlogsbyComments(String id , int maxcoment, Pageable pageable);

    Blogs getBlogbyTittleSearch(String tittleSearch);
}
