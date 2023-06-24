package com.fourTL.controller.site.Blog;

import com.fourTL.dao.BlogDAO;


import com.fourTL.dao.CategoryDAO;

import com.fourTL.entities.Blog;
import com.fourTL.DTO.BlogsDTO;
import com.fourTL.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/blog")
public class BlogRestController {

    @Autowired
    BlogDAO blDao;
    @Autowired
    // BlogService2 bls2;
    BlogsService blogsService;

    @Autowired
    CategoryDAO categoriesDAO;

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
//    public ResponseEntity<List<BlogsDTO>> getAllBlog() {
//        List<BlogsDTO> blogList = new ArrayList<>();
//
//        for (Blog bls : blogsService.findAllByOrderByCreateDateDesc()) {
//            BlogsDTO blogsDTO = new BlogsDTO();
//            blogsDTO.setId(bls.getId());
//            blogsDTO.setTittle(bls.getTittle());
//            blogsDTO.setContent(bls.getContent());
//            blogsDTO.setCreateDate(bls.getCreateDate());
//            blogsDTO.setUsername(bls.getAccount().getFullname());
//
//            blogList.add(blogsDTO);
//        }
//
//        return ResponseEntity.ok(blogList);
//    }
    // pageable
@GetMapping("getbloglist")
    public ResponseEntity<Page<BlogsDTO>> getAllBlog(@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
        List<BlogsDTO> blogList = new ArrayList<>();
   Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(9));
        for (Blog bls : blogsService.findAllByOrderByCreateDateDesc(pageable)) {
            if (bls.getStatus()==true) {

                BlogsDTO blogsDTO = new BlogsDTO();

                blogsDTO.setId(bls.getId());
                blogsDTO.setTittle(bls.getTittle());
                blogsDTO.setContent(bls.getContent());
                blogsDTO.setCreateDate(bls.getCreateDate());
                blogsDTO.setUsername(bls.getAccount().getFullname());

                blogList.add(blogsDTO);
            }
        }  

        long totalElements = blogsService.getTotalNumberOfBlogs(); // Tổng số phần tử trong danh sách

        Page<BlogsDTO> blogPage = new PageImpl<>(blogList, pageable, totalElements);

        return ResponseEntity.ok(blogPage);
    }

}
