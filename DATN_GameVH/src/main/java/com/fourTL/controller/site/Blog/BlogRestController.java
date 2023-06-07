package com.fourTL.controller.site.Blog;

import com.fourTL.dao.BlogDAO;


import com.fourTL.dao.CategoryDAO;

import com.fourTL.entities.Blog;
import com.fourTL.entities.BlogsDTO;
import com.fourTL.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping()
public class BlogRestController {

    @Autowired
    BlogDAO blDao;
    @Autowired
    // BlogService2 bls2;
    BlogsService blogsService;

    @Autowired
    CategoryDAO categoriesDAO;

    @RequestMapping("blog")

//    private ResponseEntity<HashMap<Integer, BlogsDTO>> getAllBlog() {
//
//        Map<Integer, BlogsDTO> listblog = new LinkedHashMap<>();
//
//        for (Blogs Bls : blogsService.findAllByOrderByCreateDateDesc()) {
//            BlogsDTO blogsDTO = new BlogsDTO();
//            blogsDTO.setId(Bls.getId());
//            blogsDTO.setTittle(Bls.getTittle());
//            blogsDTO.setContent(Bls.getContent());
//            blogsDTO.setCreateDate(Bls.getCreateDate());
//            blogsDTO.setUsername(Bls.getAccount().getUsername());
//            listblog.put(Bls.getId(), blogsDTO);
//        }
//        return ResponseEntity.ok((HashMap<Integer, BlogsDTO>) listblog);
//    }
    // backup xài List <> khi không gọi đc key của Hash
    public ResponseEntity<List<BlogsDTO>> getAllBlog() {
        List<BlogsDTO> blogList = new ArrayList<>();

        for (Blog bls : blogsService.findAllByOrderByCreateDateDesc()) {
            BlogsDTO blogsDTO = new BlogsDTO();
            blogsDTO.setId(bls.getId());
            blogsDTO.setTittle(bls.getTittle());
            blogsDTO.setContent(bls.getContent());
            blogsDTO.setCreateDate(bls.getCreateDate());
            blogsDTO.setUsername(bls.getAccount().getFullname());

            blogList.add(blogsDTO);
        }

        return ResponseEntity.ok(blogList);
    }

}
