package com.fourTL.service;

import java.util.List;

import com.fourTL.entities.OrderData;




public interface OrdersService {
	
	List<OrderData> findAll();

	OrderData findById(Long id);
	

}
