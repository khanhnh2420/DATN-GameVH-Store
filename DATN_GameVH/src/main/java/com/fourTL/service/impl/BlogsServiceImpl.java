package com.fourTL.service.impl;

import com.fourTL.dao.BlogsDAO;
import com.fourTL.entities.Blogs;
import com.fourTL.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class BlogsServiceImpl implements BlogsService {
   @Autowired
   BlogsDAO  blgsDao;

    @Query("SELECT MAX(ThoiGianBlog) AS LatestDate FROM Blogs")
    public List<Blogs> findNewsestBlog() {
        return blgsDao.findNewsestBlog();
    }

    public List<Blogs> findAll() {
        return blgsDao.findAll();
    }

    public Blogs findById(Integer id) {
        return blgsDao.findById(id).get();
    }
}
