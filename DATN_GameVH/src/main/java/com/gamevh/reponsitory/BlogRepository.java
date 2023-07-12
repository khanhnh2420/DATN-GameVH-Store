package com.gamevh.reponsitory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gamevh.entities.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

	// @Query("Select b from Blogs b order by b.createDate desc ")
	// List<Blogs> findNewsestBlog();
	List<Blog> findAllByOrderByCreateDateDesc();

	@Query("SELECT p FROM Blog p WHERE p.tittle LIKE ?1")
	Blog getBlogbyTittleSearch(String tittleSearch);

	// @Query("SELECT COUNT(c.Id) AS totalComment FROM Blogs b JOIN b.Comments c
	// WHERE b.Id = :IdBlog")
	@Query(value = "SELECT count (c.blog) as maxcoment FROM Blogs b INNER JOIN  b.comment c where b.id= ?1 ", nativeQuery = true)
	Page<Blog> getlistBlogsbyComments(String id, int maxcoment, Pageable pageable);

	@Query(value = "SELECT TOP(10) b FROM Blog b INNER JOIN FETCH b.comment c GROUP BY b ORDER BY COUNT(c) DESC", nativeQuery = true)
	List<Blog> getListHighestComments();

	List<Blog> findAllByStatus(Boolean status);

	@Query(value = "SELECT b.*, COUNT(c.Id) AS commentCount " + "FROM blog b " + "JOIN comment c ON b.Id = c.blog_id "
			+ "WHERE b.Status = true " + "GROUP BY b.Id " + "ORDER BY commentCount DESC "
			+ "LIMIT 4", nativeQuery = true)
	List<Blog> findTop4BlogsByCommentCountAndStatus();

}
