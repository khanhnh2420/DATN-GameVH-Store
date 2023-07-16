package com.gamevh.service;

import java.util.List;

import com.gamevh.dto.CommentDTO;

public interface CommentService {

	List<CommentDTO> findAllByBlogId(Integer blogId);

	void save(CommentDTO dto);

}
