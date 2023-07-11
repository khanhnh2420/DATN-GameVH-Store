package com.gamevh.service;

import com.gamevh.dto.BlogWithCommentsDTO;
import com.gamevh.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
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

	List<Blog> findByUsername(String username);

	List<Blog> searchBlogs(String title, String username, LocalDate createDate);

	//Blog getBlogbyID(Integer id);

	Blog findAllgetID(Integer blogid);
}
