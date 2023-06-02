package com.fourTL.controller.site.Blog;

import com.fourTL.dao.BlogsDAO;
import com.fourTL.dao.CategoriesDAO;
import com.fourTL.entities.Blogs;
import com.fourTL.entities.Categories;
import com.fourTL.service.BlogService2;
import com.fourTL.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping()
public class BlogRestController {

    @Autowired
    BlogsDAO blDao;
    @Autowired
   // BlogService2 bls2;
    BlogsService blogsService;

    @Autowired
    CategoriesDAO categoriesDAO;
    @RequestMapping("blog")
    public List<Blogs> getAllBlog(){
    return blogsService.findNewsestBlog();
    }

//    @GetMapping("/blog-detail/{id}")
//    public ResponseEntity<Blogs> getOne(@PathVariable("id") Integer id) {
//        if(!blDao.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(blDao.findById(id).get());
//    }

}
