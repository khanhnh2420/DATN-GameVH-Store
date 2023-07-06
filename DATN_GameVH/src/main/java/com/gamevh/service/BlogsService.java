package com.gamevh.service;

import com.gamevh.dto.BlogWithCommentsDTO;
import com.gamevh.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogsService {
	List<Blog> findAll();

	Blog findById(Integer id);

	List<Blog> findAllByOrderByCreateDateDesc(Pageable pageable);

	List<Blog> getListLastestBlogs();

	List<Blog> getListHighestComments();

	Page<Blog> getlistBlogsbyComments(String id, int maxcoment, Pageable pageable);

	Blog getBlogbyTittleSearch(String tittleSearch);

	public BlogWithCommentsDTO getBlogWithComments(Integer id);

	public long getTotalNumberOfBlogs();

	Blog save(Blog blog);
}
