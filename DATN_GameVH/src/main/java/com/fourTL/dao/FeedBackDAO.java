package com.fourTL.dao;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.FeedBack;

public interface FeedBackDAO extends JpaRepository<FeedBack, Integer> {
	@Query("SELECT f FROM FeedBack f WHERE f.product.id = ?1")
	List<FeedBack> findByProductId(Integer productId);

	@Query("SELECT f FROM FeedBack f WHERE f.accessory.id = ?1")
	List<FeedBack> findByAccessoryId(Integer accessoryId);
}
