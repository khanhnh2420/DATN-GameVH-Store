package com.fourTL.service;

import com.fourTL.dao.BlogDAO;
import com.fourTL.entities.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService2 {
    @Autowired
    BlogDAO bldao;

    public List<Blog> findAll(){
        return bldao.findAll();
    }
}
