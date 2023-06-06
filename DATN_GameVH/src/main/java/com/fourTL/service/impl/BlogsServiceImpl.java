package com.fourTL.service.impl;

import com.fourTL.dao.BlogDAO;
import com.fourTL.entities.Blog;
import com.fourTL.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class BlogsServiceImpl implements BlogsService {
   @Autowired
   BlogDAO blgsDao;


    //@Query("SELECT MAX(ThoiGianBlog) AS LatestDate FROM Blogs")
    @Override
    public List<Blog> findAllByOrderByCreateDateDesc() {
        return blgsDao.findAllByOrderByCreateDateDesc();
    }

    public List<Blog> findAll() {
        return blgsDao.findAll();
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
}
