package com.fourTL.controller.site.Blog;

import com.fourTL.dao.BlogWithCommentsDTO;
import com.fourTL.dao.BlogsDAO;
import com.fourTL.dao.CommentsDAO;
import com.fourTL.entities.Blogs;
import com.fourTL.entities.Comments;
import com.fourTL.service.CommentsService;
import com.fourTL.service.impl.CommentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping()
public class CommentRestController {
    @Autowired
    CommentsService commentsService;

    @Autowired
    CommentsDAO commentsDAO;
    @Autowired
    BlogsDAO blogsDAO;

//    @GetMapping("/blog-detail/{id}")
//    public List<Comments> getOneCmt(@PathVariable("id") Integer id) {
//
//        return commentsService.findAllByIdBlog(id);
//    }
//    @GetMapping("/blog-detail/{id}")
//    public ResponseEntity<Blogs,Comments> getBlogWithComments(@PathVariable("id") Integer id) {
//        Blogs blog = blogsDAO.findById(id).orElse(null);
//        if(!blogsDAO.existsById(id)) {
//            return ResponseEntity.notFound().build();
//       }
//
//        List<Comments> comments = commentsDAO.findAllById(Collections.singleton(id));
//
//        BlogWithCommentsDTO blogWithCommentsDTO = new BlogWithCommentsDTO(blog, comments);
//        return ResponseEntity.ok(blog,comments);
//    }

}
