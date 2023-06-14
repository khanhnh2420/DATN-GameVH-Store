package com.fourTL.dao;


import com.fourTL.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BlogDAO extends JpaRepository<Blog, Integer> {
    // find all theo ngay moi tạo để ở trên

   // @Query("Select b from Blogs b order by b.createDate desc ")
    //List<Blogs> findNewsestBlog();
    List<Blog> findAllByOrderByCreateDateDesc();
 @Query("SELECT p FROM Blog p WHERE p.tittle LIKE ?1")
    Blog getBlogbyTittleSearch(String tittleSearch);

   // @Query("SELECT COUNT(c.Id) AS totalComment FROM Blogs b JOIN b.Comments c WHERE b.Id = :IdBlog")
@Query(value = "SELECT count (c.blog) as maxcoment FROM Blogs b INNER JOIN  b.comment c where b.id= ?1 ", nativeQuery = true)
    Page<Blog> getlistBlogsbyComments(String id, int maxcoment, Pageable pageable);

    @Query(value = "SELECT TOP(10) b FROM Blog b INNER JOIN FETCH b.comment c GROUP BY b ORDER BY COUNT(c) DESC", nativeQuery = true)
    List<Blog> getListHighestComments();


}

