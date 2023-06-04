package com.fourTL.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fourTL.dao.Order_dataDAO;
import com.fourTL.entities.Order_data;
import com.fourTL.service.OrdersService;


@Service
public abstract class OrdersServiceImpl implements OrdersService {

	@Autowired
	Order_dataDAO ordersdao;

	@Override
	public List<Order_data> findAll() {
		return ordersdao.findAll();
	}

	@Override
	public Order_data findById(Long id) {
		return ordersdao.findById(id).get();
	}
	
}
