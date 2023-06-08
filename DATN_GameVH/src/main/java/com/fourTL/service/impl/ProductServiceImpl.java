package com.fourTL.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Product> findTopRatedProducts() {
		// TODO Auto-generated method stub
		return pDAO.findTopRatedProducts();
	}
	
}
