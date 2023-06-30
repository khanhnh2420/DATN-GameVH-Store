package com.gamevh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.entities.OrderData;
import com.gamevh.reponsitory.OrderDataRepository;
import com.gamevh.service.OrderService;


@Service
public abstract class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDataRepository ordersdao;

	@Override
	public List<OrderData> findAll() {
		return ordersdao.findAll();
	}

	@Override
	public OrderData findById(Long id) {
		return ordersdao.findById(id).get();
	}
	
}
