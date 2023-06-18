package com.fourTL.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fourTL.dao.OrderDataDAO;
import com.fourTL.entities.OrderData;
import com.fourTL.service.OrdersService;


@Service
public abstract class OrdersServiceImpl implements OrdersService {

	@Autowired
	OrderDataDAO ordersdao;

	@Override
	public List<OrderData> findAll() {
		return ordersdao.findAll();
	}

	@Override
	public OrderData findById(Long id) {
		return ordersdao.findById(id).get();
	}
	
}
