package com.fourTL.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fourTL.dao.AccessoryDAO;
import com.fourTL.entities.Accessory;
import com.fourTL.entities.Product;
import com.fourTL.service.AccessoryService;
import com.fourTL.service.ProductService;

@Service
public class AccessoryServiceImpl implements AccessoryService {

	@Autowired
	AccessoryDAO accessoryDAO;

	@Override
	public List<Accessory> findAll() {
		return accessoryDAO.findAll();
	}

	@Override
	public Accessory findById(Integer id) {
		return accessoryDAO.findById(id).get();
	}
	
}
