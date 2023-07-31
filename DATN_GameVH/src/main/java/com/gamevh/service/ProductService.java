package com.gamevh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.gamevh.entities.Product;


public interface ProductService {
	

	List<Product> findAll();

	Product findById(Integer id);
	
	Product createProduct(Product product);
	
	Product updateProduct(Product product);
	
	void deleteProduct(Product product);
	
	ResponseEntity<Object> deleteFeedbackById(Integer Id);
	List<Object[]> getReportProduct();

	List<Product> findTop5();
	
	List<Product> searchProduct(Optional<String> ProductName, Optional<String> ProductType, Optional<String> CategoryName);
}
