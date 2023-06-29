package com.fourTL.service;

import java.util.List;

import com.fourTL.entities.Feedback;

public interface FeedBackService {
	List<Feedback> findAll();

	Feedback findById(Integer id);
	
	List<Feedback> findByProductId(Integer productId);
}
