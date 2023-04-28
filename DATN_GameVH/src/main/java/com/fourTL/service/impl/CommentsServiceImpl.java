package com.fourTL.service.impl;

import com.fourTL.dao.CommentsDAO;
import com.fourTL.entities.Comments;
import com.fourTL.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    CommentsDAO cmtDao;

    @Query("SELECT MAX(ThoiGianCmt) AS LatestDate FROM Comments ")
    public List<Comments> findNewsestComment() {
        return cmtDao.findNewsestComment();
    }

    @Override
    public List<Comments> findAll() {
        return cmtDao.findAll();
    }

    @Override
    public Comments findById(Integer id) {
        return cmtDao.findById(id).get();
    }
}
