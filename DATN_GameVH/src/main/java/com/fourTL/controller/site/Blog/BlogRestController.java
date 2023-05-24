package com.fourTL.controller.site.Blog;

import com.fourTL.dao.BlogsDAO;
import com.fourTL.entities.Blogs;
import com.fourTL.service.BlogService2;
import com.fourTL.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("blog")
public class BlogRestController {

    @Autowired
    BlogsDAO blDao;
    @Autowired
    BlogService2 bls2;
    @GetMapping()
    public ResponseEntity<?> getAllBlog(){
//        List<Blogs> blogsList =blDao.findAll();
//        model.addAttribute("blogitem",blogsList);
return new ResponseEntity<>(bls2.findAll(), HttpStatus.OK);
    }

}
