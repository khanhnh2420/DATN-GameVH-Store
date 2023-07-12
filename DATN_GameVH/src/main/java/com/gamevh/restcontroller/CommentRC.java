package com.gamevh.restcontroller;

import com.gamevh.dto.BlogWithCommentsDTO;
import com.gamevh.dto.CommentDTO1;
import com.gamevh.entities.Account;
import com.gamevh.entities.Blog;
import com.gamevh.entities.Comment;
import com.gamevh.reponsitory.AccountRepository;
import com.gamevh.service.AccountService;
import com.gamevh.service.BlogsService;
import com.gamevh.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/comment")
public class CommentRC {
	@Autowired
	BlogsService blogsService;
	@Autowired
	CommentService commentsService;

	@Autowired
	AccountService accountService;

	@GetMapping("{blogId}")
	public ResponseEntity<BlogWithCommentsDTO> getAllBlog(@PathVariable("blogId") Integer blogId) {
		BlogWithCommentsDTO blogWithCommentsDTO = blogsService.getBlogWithComments(blogId);
		if (blogWithCommentsDTO != null) {

			return ResponseEntity.ok(blogWithCommentsDTO);
		}
		return ResponseEntity.notFound().build();
	}

// đợi fix bug vẫnbuggg id

//    @PostMapping("createCMT")
///*    public ResponseEntity<Object> createcmt(@RequestBody CommentDTO1 dto){
//
//        commentsService.save(dto);
//        return  ResponseEntity.ok(dto);
//    }*/
//    public ResponseEntity<Object> createCmnt(@RequestBody Comment dto,HttpServletRequest request) {
//        // Thực hiện lưu bài viết mới vào cơ sở dữ liệu
//    	HttpSession session = request.getSession();
//        List<Account> accountList = accountService.findByUsername((String)session.getAttribute("username"));
//        if (!accountList.isEmpty()) {
//            Account account = accountList.get(0); // Lấy tài khoản đầu tiên trong danh sách
//            dto.setAccount(account);
//        }
//        
//        Comment savedto = commentsService.save(dto);
//        return ResponseEntity.ok(savedto);
//    }

	@PostMapping("createcmt/{id}")
	public ResponseEntity<Comment> createComment(@PathVariable("id") Integer blogId, @RequestBody Comment comment,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Account> accountList = accountService.findByUsername((String) session.getAttribute("username"));

		if (!accountList.isEmpty()) {
			Account account = accountList.get(0); // Lấy tài khoản đầu tiên trong danh sách
			Blog blogget = blogsService.findAllgetID(blogId);

			// Gán blogId và accountId cho Comment
			
			comment.setAccount(account);
			comment.setBlog(blogget);
			comment.setStatus(false);
		}
		// Lưu Comment vào cơ sở dữ liệu
		Comment savedComment = commentsService.save(comment);

		return ResponseEntity.ok(savedComment);
	}
}
