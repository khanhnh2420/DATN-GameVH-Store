package com.gamevh.service;

import java.util.List;

import com.gamevh.entities.Category;

public interface CategoryService {
	List<Category> findAll();

	Category findById(Integer id);
}
