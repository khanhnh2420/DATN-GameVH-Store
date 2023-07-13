package com.gamevh.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamevh.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	List<Comment> findByBlogIdAndStatus(Integer blogId, Boolean status);
}
