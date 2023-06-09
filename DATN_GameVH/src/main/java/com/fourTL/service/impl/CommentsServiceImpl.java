package com.fourTL.service.impl;


import com.fourTL.dao.BlogWithCommentsDTO;
import com.fourTL.dao.CommentDAO;
import com.fourTL.entities.Comment;
import com.fourTL.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    CommentDAO cmtDao;




//    @Query("SELECT MAX(ThoiGianCmt) AS LatestDate FROM Comments ")
//    public List<Comments> findNewsestComment() {
//        return cmtDao.findNewsestComment();
//    }

    @Override
    public List<Comment> findAll() {
        return cmtDao.findAll();
    }

//  @Override
//    public List<Comment> findAllByIdBlog(Integer id){
//        return cmtDao.findAllByIdBlog(id);
//    }

    public List<Comment> findAllById(Iterable<Integer> integers) {
        return cmtDao.findAllById(integers);
    }

    @Override
    public  List<Comment> findAllCommentAndBlogByIdBlog(Integer blogId){
        return cmtDao.findAllCommentAndBlogByIdBlog(blogId);
    }
}
