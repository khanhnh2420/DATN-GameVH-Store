package com.fourTL.service;

import com.fourTL.entities.Comments;


import java.util.List;

public interface CommentsService {
    List<Comments> findAll();

    List<Comments> findAllByIdBlog(Integer id);

    Comments findById(Integer id);

}
