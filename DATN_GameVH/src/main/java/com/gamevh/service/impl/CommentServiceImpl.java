package com.gamevh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.dto.CommentDTO;
import com.gamevh.entities.Comment;
import com.gamevh.repository.CommentRepository;
import com.gamevh.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Override
	public List<CommentDTO> findAllByBlogId(Integer blogId) {
		List<Comment> comments = commentRepository.findByBlogIdAndStatus(blogId, true);
		List<CommentDTO> commentDTOs = new ArrayList<>();
		for (Comment comment : comments) {
			CommentDTO commentDTO = new CommentDTO();
			// Thực hiện sao chép các thuộc tính từ đối tượng Comment sang đối tượng CommentDTO
			commentDTO.setId(comment.getId());
			commentDTO.setContent(comment.getContent());
			commentDTO.setCreateDate(comment.getCreateDate());
			commentDTO.setUsername(comment.getAccount().getUsername());
			commentDTO.setAvatarUser(comment.getAccount().getPhoto());
			
			commentDTOs.add(commentDTO);
		}
		return commentDTOs;
	}

}
