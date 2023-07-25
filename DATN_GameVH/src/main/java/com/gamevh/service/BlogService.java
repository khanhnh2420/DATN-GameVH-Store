package com.gamevh.service;

import java.util.List;

import com.gamevh.dto.BlogDTO;

public interface BlogService {

	List<BlogDTO> findAll();

	List<BlogDTO> findAllByStatus(Boolean status);

	List<BlogDTO> findTop4BlogsByCommentCountAndStatus();

	BlogDTO findById(Integer id);
}
