package com.gamevh.service;

import java.util.List;

import com.gamevh.entities.Product;

public interface ProductService {

	List<Product> findAll();

	Product findById(Integer id);
	
	
}
