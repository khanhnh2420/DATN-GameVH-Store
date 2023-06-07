package com.fourTL.controller.site.Blog;

import com.fourTL.dao.BlogWithCommentsDTO;
import com.fourTL.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/blog-detail")
public class CommentRestController {

    @Autowired
    BlogsService blogsService;


    @GetMapping("/{blogId}")
    public ResponseEntity<BlogWithCommentsDTO> getAllBlog(@PathVariable("blogId") Integer blogId) {
        BlogWithCommentsDTO blogWithCommentsDTO = blogsService.getBlogWithComments(blogId);
        if (blogWithCommentsDTO != null) {

            return ResponseEntity.ok(blogWithCommentsDTO);
        }
        return ResponseEntity.notFound().build();
    }


}
