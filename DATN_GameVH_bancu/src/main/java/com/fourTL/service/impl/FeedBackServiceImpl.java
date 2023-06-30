package com.fourTL.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fourTL.dao.FeedBackDAO;
import com.fourTL.entities.Feedback;
import com.fourTL.service.FeedBackService;

@Service
public class FeedBackServiceImpl implements FeedBackService{

	@Autowired
	FeedBackDAO feedBackDAO;
	
	@Override
	public List<Feedback> findAll() {
		return feedBackDAO.findAll();
	}

	@Override
	public Feedback findById(Integer id) {
		return feedBackDAO.findById(id).get();
	}

	@Override
	public List<Feedback> findByProductId(Integer productId) {
		return feedBackDAO.findByProductId(productId);
	}
}
