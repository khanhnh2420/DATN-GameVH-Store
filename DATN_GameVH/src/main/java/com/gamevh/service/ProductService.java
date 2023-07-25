package com.gamevh.service;

import java.util.List;



import com.gamevh.entities.Product;


public interface ProductService {
	

	List<Product> findAll();

	Product findById(Integer id);
	
	Product createProduct(Product product);
	
	Product updateProduct(Product product);
	
	void deleteProduct(Product product);
	
	List<Product> searchProducts(String productName, String productType, String categoryName);
	
}
