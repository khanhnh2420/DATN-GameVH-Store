package com.fourTL.service.impl;

import com.fourTL.dao.BlogsDAO;
import com.fourTL.entities.Blogs;
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
   BlogsDAO  blgsDao;


    //@Query("SELECT MAX(ThoiGianBlog) AS LatestDate FROM Blogs")
    @Override
    public List<Blogs> findAllByOrderByCreateDateDesc() {
        return blgsDao.findAllByOrderByCreateDateDesc();
    }

    public List<Blogs> findAll() {
        return blgsDao.findAll();
    }

    public Blogs findById(Integer id) {
        return blgsDao.findById(id).get();
    }

    @Override
    public List<Blogs> getListLastestBlogs() {
        return null;
    }

    @Override
    public List<Blogs> getListHighestComments() {
        return blgsDao.getListHighestComments();
    }

    @Override
    public Page<Blogs> getlistBlogsbyComments(String id, int maxcoment, Pageable pageable) {
        return blgsDao.getlistBlogsbyComments(id, maxcoment,pageable);
    }

    @Override
    public Blogs getBlogbyTittleSearch(String tittleSearch) {
        return blgsDao.getBlogbyTittleSearch(tittleSearch);
    }
}
