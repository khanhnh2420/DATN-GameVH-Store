package com.fourTL.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Feedback;

public interface FeedBackDAO extends JpaRepository<Feedback, Integer> {
	List<Feedback> findByProductId(Integer productId);
}
