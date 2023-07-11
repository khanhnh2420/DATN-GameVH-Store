package com.gamevh.service.impl;

import java.util.List;

import com.gamevh.ExceptionHandling.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gamevh.entities.Category;
import com.gamevh.reponsitory.CategoryRepository;
import com.gamevh.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findById(Integer id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public Category addCategory(Category category){
		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(String categoryId, Category newCategory){
		Category category = categoryRepository.findByCategoryId(categoryId)
				.orElseThrow(() -> new CustomException
						("Không tìm thấy danh mục sản phẩm với id " + categoryId, HttpStatus.NOT_FOUND));
		category.setCategoryId(newCategory.getCategoryId());
		category.setName(newCategory.getName());
		category.setType(newCategory.getType());
		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(String categoryId){
		Category category = categoryRepository.findByCategoryId(categoryId)
				.orElseThrow(() -> new CustomException
						("Không tìm thấy danh mục sản phẩm với id " + categoryId, HttpStatus.NOT_FOUND));
		categoryRepository.delete(category);
	}
	
}
