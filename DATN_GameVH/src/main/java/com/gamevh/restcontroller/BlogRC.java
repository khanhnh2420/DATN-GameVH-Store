package com.gamevh.restcontroller;

import com.gamevh.dto.BlogsDTO;
import com.gamevh.entities.Blog;
import com.gamevh.reponsitory.CategoryRepository;
import com.gamevh.service.BlogsService;

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
public class BlogRC {
	@Autowired
    BlogsService blogsService;

    @Autowired
    CategoryRepository categoriesDAO;
    @GetMapping("/getbloglist")
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
