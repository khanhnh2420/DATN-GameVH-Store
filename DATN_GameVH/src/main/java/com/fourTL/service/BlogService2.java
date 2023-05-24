package com.fourTL.service;

import com.fourTL.dao.BlogsDAO;
import com.fourTL.entities.Blogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService2 {
    @Autowired
    BlogsDAO bldao;

    public List<Blogs> findAll(){
        return bldao.findAll();
    }
}
