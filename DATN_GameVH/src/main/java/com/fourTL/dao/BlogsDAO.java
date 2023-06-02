package com.fourTL.dao;

import com.fourTL.entities.Blogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BlogsDAO extends JpaRepository<Blogs, Integer> {
    // find all theo ngay moi tạo để ở trên
 //@Query("SELECT MAX(b.ThoiGianBlog) AS LatestDate FROM  Blogs b")
    @Query("Select b from Blogs b order by b.ThoiGianBlog desc ")
    List<Blogs> findNewsestBlog();

 @Query("SELECT p FROM Blogs p WHERE p.BlogTittle LIKE ?1")
    Blogs getBlogbyTittleSearch(String tittleSearch);

   // @Query("SELECT COUNT(c.Id) AS totalComment FROM Blogs b JOIN b.Comments c WHERE b.Id = :IdBlog")
@Query(value = "SELECT count (c.blogs) as maxcoment FROM Blogs b INNER JOIN  b.comments c where b.id= ?1 ", nativeQuery = true)
    Page<Blogs> getlistBlogsbyComments(String id, int maxcoment, Pageable pageable);

    @Query(value = "SELECT TOP(10) b FROM Blogs b INNER JOIN FETCH b.comments c GROUP BY b ORDER BY COUNT(c) DESC", nativeQuery = true)
    List<Blogs> getListHighestComments();
}

