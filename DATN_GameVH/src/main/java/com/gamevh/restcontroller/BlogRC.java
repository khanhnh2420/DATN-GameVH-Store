package com.gamevh.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamevh.dto.BlogDTO;
import com.gamevh.service.BlogService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/blog")
public class BlogRC {
	
	@Autowired
	BlogService blogService;
	
	@GetMapping("getAllBlog")
	public ResponseEntity<List<BlogDTO>> getAllBlogDTOByStatus() {
		// Lấy danh sách blog có status = true
		List<BlogDTO> lstBlog = blogService.findAllByStatus(true);
		return ResponseEntity.ok(lstBlog);
	}
	
	@GetMapping("getAll")
	public ResponseEntity<List<BlogDTO>> getAllBlogDTO() {
		// Lấy toàn bộ danh sách blog
		List<BlogDTO> lstBlog = blogService.findAll();
		return ResponseEntity.ok(lstBlog);
	}
	
	@GetMapping("getTop4BlogPopular")
	public ResponseEntity<List<BlogDTO>> getTop4BlogPopular() {
		// Lấy danh sách blog có status = true
		List<BlogDTO> lstBlog = blogService.findTop4BlogsByCommentCountAndStatus();
		return ResponseEntity.ok(lstBlog);
	}
	
	@GetMapping("getBlogDetail/{blogId}")
	public ResponseEntity<BlogDTO> getBlogDTOById(@PathVariable("blogId") Integer blogId) {
		return ResponseEntity.ok(blogService.findById(blogId));
	}
}
