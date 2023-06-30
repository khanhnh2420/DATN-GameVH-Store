package com.fourTL.service;

import java.util.List;

import com.fourTL.entities.Category;

public interface CategoryService {
	List<Category> findAll();

	Category findById(Integer id);
}
