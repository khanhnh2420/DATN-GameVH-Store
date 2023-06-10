package com.fourTL.service;

import java.util.List;

import com.fourTL.entities.FeedBack;

public interface FeedBackService {
	List<FeedBack> findAll();

	FeedBack findById(Integer id);
	
	List<FeedBack> findByProductId(Integer productId);
	
	List<FeedBack> findByAccessoryId(Integer accessoryId);
}
