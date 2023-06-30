package com.gamevh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.entities.Product;
import com.gamevh.reponsitory.ProductRepository;
import com.gamevh.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository pDAO;

	@Override
	public List<Product> findAll() {
		return pDAO.findAll();
	}

	@Override
	public Product findById(Integer id) {
		return pDAO.findById(id).get();
	}
}
