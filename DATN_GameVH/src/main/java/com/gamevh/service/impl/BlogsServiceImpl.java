package com.gamevh.service.impl;



import com.gamevh.dto.BlogWithCommentsDTO;
import com.gamevh.entities.Blog;
import com.gamevh.entities.Comment;
import com.gamevh.reponsitory.BlogRepository;
import com.gamevh.reponsitory.CommentRepository;
import com.gamevh.search.BlogSearchMany;
import com.gamevh.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Service
public class BlogsServiceImpl implements BlogsService {
   @Autowired
   BlogRepository blgsDao;
   @Autowired
   CommentRepository commentDAO;


    //@Query("SELECT MAX(ThoiGianBlog) AS LatestDate FROM Blogs")
    @Override
    public List<Blog> findAllByOrderByCreateDateDesc(Pageable pageable) {
        return blgsDao.findAllByOrderByCreateDateDesc();
    }

    public List<Blog> findAll() {
        return blgsDao.findAll();
    }
    public Blog save(Blog blog) {
    	return blgsDao.save(blog);
    }
    public Blog findById(Integer id) {
        return blgsDao.findById(id).get();
    }

    @Override
    public List<Blog> getListLastestBlogs() {
        return null;
    }

    @Override
    public List<Blog> getListHighestComments() {
        return blgsDao.getListHighestComments();
    }

    @Override
    public Page<Blog> getlistBlogsbyComments(String id, int maxcoment, Pageable pageable) {
        return blgsDao.getlistBlogsbyComments(id, maxcoment,pageable);
    }

    @Override
    public Blog getBlogbyTittleSearch(String tittleSearch) {
        return blgsDao.getBlogbyTittleSearch(tittleSearch);
    }


    public BlogWithCommentsDTO getBlogWithComments(Integer id) {
        Blog blog = blgsDao.findById(id).orElse(null);
        if (blog != null) {
            List<Comment> comments = commentDAO.findByBlogId(id);
            Collections.sort(comments, Comparator.comparingLong(Comment::getId));
             Collections.reverse(comments);
            return new BlogWithCommentsDTO(blog, comments);
        }

        return null;
    }
    @Override
    public long getTotalNumberOfBlogs() {
        return blgsDao.count();
    }
    
    @Override
    public List<Blog> findByUsername(String username) {
        return blgsDao.findByAccount_Username(username);
    }
    @Override
    public List<Blog> searchBlogs(String title, String username, LocalDate createDate) {
        Specification<Blog> spec = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            spec = spec.or(BlogSearchMany.hasTitle(title));
        }

        if (username != null && !username.isEmpty()) {
            spec = spec.or(BlogSearchMany.hasUsername(username));
        }

        if (createDate != null) {
            spec = spec.or(BlogSearchMany.hasCreateDate(createDate));
        }

        return blgsDao.findAll(spec);
    }
}
