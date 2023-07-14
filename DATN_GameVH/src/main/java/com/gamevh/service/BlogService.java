package com.gamevh.service;

import java.util.List;

import com.gamevh.dto.BlogDTO;
import com.gamevh.entities.Blog;

public interface BlogService {

	Blog findById(Integer id);

	List<Blog> findAll();

	List<BlogDTO> findAllByStatus(Boolean status);

	List<BlogDTO> findTop4BlogsByCommentCountAndStatus();

}
