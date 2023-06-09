package com.fourTL.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fourTL.DTO.ProductDTO;
import com.fourTL.entities.Product;

public interface ProductService {

	List<Product> findAll();

	Product findById(Integer id);
	
	List<ProductDTO> findTopRatedProducts();
	
	Page<ProductDTO> findAllProductDTO(Pageable pageable);
}
