package com.fourTL.dao;


import com.fourTL.entities.Blogs;
import com.fourTL.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsDAO extends JpaRepository<Comments, Integer> {
    @Query("SELECT MAX(ThoiGianCmt) AS LatestDate FROM Comments ")
    List<Comments> findNewsestComment();

    List<Comments> findAllByIdBlog(Integer id);
}
