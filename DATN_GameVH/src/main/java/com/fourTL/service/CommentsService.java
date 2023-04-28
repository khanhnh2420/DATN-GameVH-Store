package com.fourTL.service;

import com.fourTL.entities.Comments;
import com.fourTL.entities.Products;

import java.util.List;

public interface CommentsService {
    List<Comments> findAll();

    Comments findById(Integer id);
}
