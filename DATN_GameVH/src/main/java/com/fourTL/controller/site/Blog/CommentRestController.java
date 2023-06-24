package com.fourTL.controller.site.Blog;

import com.fourTL.DTO.BlogWithCommentsDTO;
import com.fourTL.DTO.CommentDTO;
import com.fourTL.DTO.CommentDTO1;
import com.fourTL.dao.AccountDAO;
import com.fourTL.dao.CommentDAO;
import com.fourTL.entities.Account;
import com.fourTL.entities.Blog;
import com.fourTL.entities.Comment;
import com.fourTL.service.BlogsService;
import com.fourTL.service.CommentsService;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin("*")
@RestController
@RequestMapping("/blog-detail")
public class CommentRestController {

    @Autowired
    BlogsService blogsService;
@Autowired
    CommentsService commentsService;

    @GetMapping("/{blogId}")
    public ResponseEntity<BlogWithCommentsDTO> getAllBlog(@PathVariable("blogId") Integer blogId) {
        BlogWithCommentsDTO blogWithCommentsDTO = blogsService.getBlogWithComments(blogId);
        if (blogWithCommentsDTO != null) {

            return ResponseEntity.ok(blogWithCommentsDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @Autowired
    AccountDAO accountDAO;

// đợi fix bug vẫnbuggg id

    @PostMapping("/createCMT")
    public ResponseEntity<Object> createcmt(@RequestBody CommentDTO1 dto){

     commentsService.save(dto);
    return  ResponseEntity.ok(dto);
    }
    @GetMapping("/create")
    public List<Comment> getAll(){
        return commentsService.findAll();
    }


}
