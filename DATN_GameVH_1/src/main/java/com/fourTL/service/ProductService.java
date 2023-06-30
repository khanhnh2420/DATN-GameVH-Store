package com.fourTL.service;

import java.util.List;

import com.fourTL.DTO.ProductDTO;
import com.fourTL.entities.Product;

public interface ProductService {

	List<Product> findAll();

	Product findById(Integer id);
	
	List<ProductDTO> findProductFeedBack();
}
