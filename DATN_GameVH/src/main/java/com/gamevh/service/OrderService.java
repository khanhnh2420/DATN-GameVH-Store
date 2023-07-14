package com.gamevh.service;

import java.util.List;

import com.gamevh.entities.OrderData;




public interface OrderService {
	
	List<OrderData> findAll();

	OrderData findById(Long id);
	

}
