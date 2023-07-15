package com.gamevh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.dto.CommentDTO;
import com.gamevh.entities.Account;
import com.gamevh.entities.Comment;
import com.gamevh.repository.AccountRepository;
import com.gamevh.repository.BlogRepository;
import com.gamevh.repository.CommentRepository;
import com.gamevh.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	BlogRepository blogRepository;

	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<CommentDTO> findAllByBlogId(Integer blogId) {
		List<Comment> comments = commentRepository.findByBlogIdAndStatus(blogId, true);
		List<CommentDTO> commentDTOs = new ArrayList<>();
		for (Comment comment : comments) {
			CommentDTO commentDTO = new CommentDTO();
			// Thực hiện sao chép các thuộc tính từ đối tượng Comment sang đối tượng
			// CommentDTO
			commentDTO.setId(comment.getId());
			commentDTO.setContent(comment.getContent());
			commentDTO.setCreateDate(comment.getCreateDate());
			commentDTO.setUsername(comment.getAccount().getUsername());
			commentDTO.setAvatarUser(comment.getAccount().getPhoto());

			commentDTOs.add(commentDTO);
		}
		return commentDTOs;
	}

	@Override
	public void save(CommentDTO dto) {
		Comment entity = new Comment();
		entity.setContent(dto.getContent());
		entity.setCreateDate(new Date());
		entity.setStatus(false); //trạng thái mặc định cho comment luôn là false để chờ kiểm duyệt

		Account account = accountRepository.findByUsername(dto.getUsername());
		if (account != null) {
			entity.setAccount(account);
		} else {
			// Xử lý trường hợp không tìm thấy tài khoản
			// Ví dụ: throw một Exception hoặc gán giá trị mặc định cho account
		}

		entity.setBlog(blogRepository.findById(dto.getBlogId()).orElse(null));
		commentRepository.save(entity);
	}

}
