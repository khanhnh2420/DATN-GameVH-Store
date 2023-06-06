package com.fourTL.controller.site.Blog;

import com.fourTL.dao.BlogDAO;
import com.fourTL.dao.CommentDAO;
import com.fourTL.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping()
public class CommentRestController {
    @Autowired
    CommentsService commentsService;

    @Autowired
    CommentDAO commentsDAO;
    @Autowired
    BlogDAO blogsDAO;

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
