package com.gamevh.service;

import java.util.List;

import com.gamevh.dto.FullOrderDTO;
import com.gamevh.entities.OrderData;




public interface OrderService {
	
	List<OrderData> findAll();

	FullOrderDTO findOne(String orderId);

	OrderData findById(Long id);
	

}
