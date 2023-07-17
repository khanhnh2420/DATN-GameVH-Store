package com.gamevh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.entities.OrderData;
import com.gamevh.repository.OrderDataRepository;
import com.gamevh.service.OrderService;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

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

	@Override
	public OrderData add(OrderData orderData) {
		return orderDataRepository.save(orderData);
	}

	@Override
	public List<OrderData> findByOrderId(String orderId) {
		return orderDataRepository.findByOrderId(orderId);
	}
	
}
