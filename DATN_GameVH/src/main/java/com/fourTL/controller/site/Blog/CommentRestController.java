package com.fourTL.controller.site.Blog;

import com.fourTL.dao.BlogDAO;
import com.fourTL.dao.BlogWithCommentsDTO;
import com.fourTL.dao.CommentDAO;
import com.fourTL.entities.Blog;
import com.fourTL.entities.BlogsDTO;
import com.fourTL.entities.Comment;
import com.fourTL.entities.Product;
import com.fourTL.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/blog-detail")
public class CommentRestController {
    @Autowired
    CommentsService commentsService;

    @Autowired
    CommentDAO commentsDAO;


//    @GetMapping("/{blogId}")
//    public ResponseEntity<List<Comment>> getCommentsByBlogId(@PathVariable("blogId")  Integer blogId) {
//        return ResponseEntity.ok(commentsService.findAllCommentAndBlogByIdBlog(blogId));
//
//    }
    @GetMapping("/{blogId}")
//    public ResponseEntity<List<Comment>> getCommentsByBlogId2() {
//        return ResponseEntity.ok(commentsService.findAll());
//
//    }

    public ResponseEntity<List<BlogWithCommentsDTO>> getAllBlog(@PathVariable("blogId")  Integer blogId) {
        List<BlogWithCommentsDTO> blogList = new ArrayList<>();

        for (Comment bls : commentsService.findAllCommentAndBlogByIdBlog(blogId)) {
            BlogWithCommentsDTO blogsDTO = new BlogWithCommentsDTO();
            blogsDTO.setId(bls.getId());
          //  blogsDTO.setContent(bls.getContent());
            blogsDTO.setCreateDate(bls.getCreateDate());
            blogsDTO.setUsername(bls.getAccount().getUsername());

            // blog
          blogsDTO.setBlogId(bls.getBlog().getId());
        blogsDTO.setUsername_blog(bls.getBlog().getAccount().getUsername());
            blogList.add(blogsDTO);
        }

        return ResponseEntity.ok(blogList);
    }


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
