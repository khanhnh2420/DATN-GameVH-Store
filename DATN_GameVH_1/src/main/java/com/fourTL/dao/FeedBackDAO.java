package com.fourTL.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fourTL.entities.Feedback;

public interface FeedBackDAO extends JpaRepository<Feedback, Integer> {
	@Query("SELECT f FROM Feedback f WHERE product.id = :productId AND status = 1")
	List<Feedback> findByProductId(Integer productId);
}
