package com.gamevh.service;

import java.util.List;

import com.gamevh.entities.Feedback;

public interface FeedbackService {
	List<Feedback> findAll();

	Feedback findById(Integer id);
	
	List<Feedback> findByProductId(Integer productId);
}
