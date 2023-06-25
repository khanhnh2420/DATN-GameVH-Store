package com.fourTL.dao;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Feedback;

public interface FeedBackDAO extends JpaRepository<Feedback, Integer> {
	@Query("SELECT f FROM Feedback f WHERE f.product.id = ?1")
	List<Feedback> findByProductId(Integer productId);

	@Query("SELECT f FROM Feedback f WHERE f.accessory.id = ?1")
	List<Feedback> findByAccessoryId(Integer accessoryId);
}
