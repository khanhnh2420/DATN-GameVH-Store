package com.gamevh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.entities.Feedback;
import com.gamevh.reponsitory.FeedbackRepository;
import com.gamevh.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService{

	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Override
	public List<Feedback> findAll() {
		return feedbackRepository.findAll();
	}

	@Override
	public Feedback findById(Integer id) {
		return feedbackRepository.findById(id).get();
	}

	@Override
	public List<Feedback> findByProductId(Integer productId) {
		return feedbackRepository.findByProductId(productId);
	}
}
