package com.fourTL.service;

import com.fourTL.entities.Blogs;

import java.util.List;

public interface BlogsService {
List<Blogs> findAll();
Blogs findById(Integer id);

}
