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
	OrderDataRepository orderDataRepository;

	@Override
	public List<OrderData> findAll() {
		return orderDataRepository.findAll();
	}

	@Override
	public OrderData findById(Long id) {
		return orderDataRepository.findById(id).get();
	}
	
}
