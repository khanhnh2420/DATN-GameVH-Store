package com.gamevh.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.entities.Product;
import com.gamevh.repository.ProductRepository;
import com.gamevh.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findById(Integer id) {
		return productRepository.findById(id).get();
	}

	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	

	@Override
	public Product updateProduct(Product product) {
		 return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Product product) {
		 productRepository.delete(product);
	}

	

	@Override
	public List<Object[]> getReportProduct() {
		return productRepository.reportProduct();
	}


	
}
