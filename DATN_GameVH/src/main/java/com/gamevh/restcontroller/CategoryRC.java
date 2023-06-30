package com.gamevh.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamevh.entities.Category;
import com.gamevh.entities.Product;
import com.gamevh.service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/category")
public class CategoryRC {
	@Autowired
	CategoryService categoryService;

	@GetMapping("/getAll")
	public ResponseEntity<List<Category>> getAllCategories() {
	    return ResponseEntity.ok(categoryService.findAll());
	}
	
	@GetMapping("/getProduct/{categoryId}")
	public ResponseEntity<List<Product>> getAllProductByCategory(@PathVariable("categoryId") Integer categoryId) {
	    return ResponseEntity.ok(categoryService.findById(categoryId).getProduct());
	}
}
