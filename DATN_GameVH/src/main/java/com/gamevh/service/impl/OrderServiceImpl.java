package com.gamevh.service.impl;

import java.util.List;

import com.gamevh.ExceptionHandling.CustomException;
import com.gamevh.dto.FullOrderDTO;
import com.gamevh.mapper.impl.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gamevh.entities.OrderData;
import com.gamevh.reponsitory.OrderDataRepository;
import com.gamevh.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDataRepository ordersdao;
	@Autowired
	OrderMapper orderMapper;

	@Override
	public List<OrderData> findAll() {
		return ordersdao.findAll();
	}

	@Override
	public FullOrderDTO findOne(String orderId){
		OrderData orderData = ordersdao.findByOrderId(orderId)
				.orElseThrow(() ->
						new CustomException("Không tìm thấy order với mã là " + orderId, HttpStatus.NOT_FOUND));
		return orderMapper.orderToFullOrder(orderData);
	}

	@Override
	public OrderData findById(Long id) {
		return ordersdao.findById(id).get();
	}
	
}
