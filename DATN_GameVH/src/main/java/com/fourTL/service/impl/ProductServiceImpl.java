package com.fourTL.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fourTL.DTO.ProductDTO;
import com.fourTL.dao.ProductDAO;
import com.fourTL.entities.Product;
import com.fourTL.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO pDAO;

	@Override
	public List<Product> findAll() {
		return pDAO.findAll();
	}

	@Override
	public Product findById(Integer id) {
		return pDAO.findById(id).get();
	}

	@Override
	public List<ProductDTO> findTopRatedProducts() {
		// TODO Auto-generated method stub
		return pDAO.findTopRatedProducts();
	}
	
	@Override
	public Page<ProductDTO> findAllProductDTO(Pageable pageable){
		// TODO Auto-generated method stub
		return pDAO.findAllProductDTO(pageable);
	}
}
