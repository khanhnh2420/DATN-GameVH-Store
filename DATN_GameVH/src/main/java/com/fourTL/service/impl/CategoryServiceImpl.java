package com.fourTL.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fourTL.dao.CategoryDAO;
import com.fourTL.dao.FeedBackDAO;
import com.fourTL.entities.Category;
import com.fourTL.entities.Feedback;
import com.fourTL.service.CategoryService;
import com.fourTL.service.FeedBackService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryDAO categoryDAO;
	
	@Override
	public List<Category> findAll() {
		return categoryDAO.findAll();
	}

	@Override
	public Category findById(Integer id) {
		return categoryDAO.findById(id).get();
	}

	
}
