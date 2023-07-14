package com.gamevh.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamevh.dto.CommentDTO;
import com.gamevh.service.CommentService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/comment")
public class CommentRC {

	@Autowired
	CommentService commentService;
	
	@GetMapping("getComments/{blogId}")
	public ResponseEntity<List<CommentDTO>> getAllCommentByBlogId(@PathVariable("blogId") Integer blogId) {
		return ResponseEntity.ok(commentService.findAllByBlogId(blogId));
	}
}
