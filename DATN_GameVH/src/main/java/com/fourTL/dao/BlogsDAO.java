package com.fourTL.dao;

import com.fourTL.entities.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BlogsDAO extends JpaRepository<Blogs, Integer> {
 @Query("SELECT MAX(ThoiGianBlog) AS LatestDate FROM Blogs")
    List<Blogs> findNewsestBlog();
}
