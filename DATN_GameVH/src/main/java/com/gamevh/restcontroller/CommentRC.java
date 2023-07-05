package com.gamevh.restcontroller;

import com.gamevh.dto.BlogWithCommentsDTO;
import com.gamevh.dto.CommentDTO1;
import com.gamevh.reponsitory.AccountRepository;
import com.gamevh.service.BlogsService;
import com.gamevh.service.CommentService;
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

    @GetMapping("{blogId}")
    public ResponseEntity<BlogWithCommentsDTO> getAllBlog(@PathVariable("blogId") Integer blogId) {
        BlogWithCommentsDTO blogWithCommentsDTO = blogsService.getBlogWithComments(blogId);
        if (blogWithCommentsDTO != null) {

            return ResponseEntity.ok(blogWithCommentsDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @Autowired
    AccountRepository accountDAO;

// đợi fix bug vẫnbuggg id

    @PostMapping("createCMT")
    public ResponseEntity<Object> createcmt(@RequestBody CommentDTO1 dto){

        commentsService.save(dto);
        return  ResponseEntity.ok(dto);
    }
}
