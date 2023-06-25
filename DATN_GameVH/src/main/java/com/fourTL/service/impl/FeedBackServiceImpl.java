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
		// TODO Auto-generated method stub
		return feedBackDAO.findAll();
	}

	@Override
	public Feedback findById(Integer id) {
		// TODO Auto-generated method stub
		return feedBackDAO.findById(id).get();
	}

	@Override
	public List<Feedback> findByProductId(Integer productId) {
		// TODO Auto-generated method stub
		return feedBackDAO.findByProductId(productId);
	}

	@Override
	public List<Feedback> findByAccessoryId(Integer accessoryId) {
		// TODO Auto-generated method stub
		return feedBackDAO.findByAccessoryId(accessoryId);
	}

}
