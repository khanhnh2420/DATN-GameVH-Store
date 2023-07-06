package com.gamevh.restcontroller;

import com.gamevh.dto.BlogsDTO;
import com.gamevh.entities.Account;
import com.gamevh.entities.Blog;
import com.gamevh.reponsitory.CategoryRepository;
import com.gamevh.service.AccountService;
import com.gamevh.service.BlogsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	AccountService accountService;

    @Autowired
    CategoryRepository categoriesDAO;
    // dùng cho blog trang chủ và blog admin get all
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
    // tạo blog ở phần admin
    @PostMapping("createBlog")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog,HttpServletRequest request) {
        // Thực hiện lưu bài viết mới vào cơ sở dữ liệu
    	HttpSession session = request.getSession();
        List<Account> accountList = accountService.findByUsername((String)session.getAttribute("username"));
        if (!accountList.isEmpty()) {
            Account account = accountList.get(0); // Lấy tài khoản đầu tiên trong danh sách
            blog.setAccount(account);
        }
        
        Blog savedBlog = blogsService.save(blog);
        return ResponseEntity.ok(savedBlog);
    }
    @PutMapping("blogEdit/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Integer id, @RequestBody Blog updatedBlog) {
        // Kiểm tra xem bài viết có tồn tại trong cơ sở dữ liệu không
        Blog existingBlog = blogsService.findById(id);
        if (existingBlog == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Cập nhật thông tin của bài viết
        existingBlog.setTittle(updatedBlog.getTittle());
        existingBlog.setCreateDate(updatedBlog.getCreateDate());
        existingBlog.setStatus(updatedBlog.getStatus());
        existingBlog.setContent(updatedBlog.getContent());
        
        // Thực hiện lưu bài viết đã được cập nhật vào cơ sở dữ liệu
        Blog updateb = blogsService.save(existingBlog);
        return ResponseEntity.ok(updateb);
    }
    
    @PutMapping("blogsDelete/{id}/delete")
    public ResponseEntity<Void> deleteBlog(@PathVariable Integer id) {
        // Kiểm tra xem đối tượng Blog có tồn tại trong cơ sở dữ liệu không
        Blog optionalBlog = blogsService.findById(id);
        if( optionalBlog==null) {
        	 return ResponseEntity.notFound().build();
        	 }
           
            // Đặt trạng thái của đối tượng Blog thành 0
        optionalBlog.setStatus(false);
            blogsService.save(optionalBlog);
            return ResponseEntity.noContent().build();
       
        
    }}
