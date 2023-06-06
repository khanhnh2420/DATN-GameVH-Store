package com.fourTL.controller.site.Blog;

import com.fourTL.dao.BlogsDAO;
import com.fourTL.dao.CategoriesDAO;
import com.fourTL.entities.Blogs;
import com.fourTL.entities.BlogsDTO;
import com.fourTL.entities.Categories;
import com.fourTL.service.BlogService2;
import com.fourTL.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

        for (Blogs bls : blogsService.findAllByOrderByCreateDateDesc()) {
            BlogsDTO blogsDTO = new BlogsDTO();
            blogsDTO.setId(bls.getId());
            blogsDTO.setTittle(bls.getTittle());
            blogsDTO.setContent(bls.getContent());
            blogsDTO.setCreateDate(bls.getCreateDate());
            blogsDTO.setUsername(bls.getAccount().getUsername());

            blogList.add(blogsDTO);
        }

        return ResponseEntity.ok(blogList);
    }

//    @GetMapping("/blog-detail/{id}")
//    public ResponseEntity<Blogs> getOne(@PathVariable("id") Integer id) {
//        if(!blDao.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(blDao.findById(id).get());
//    }

}
