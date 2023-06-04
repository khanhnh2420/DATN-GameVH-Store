package com.fourTL.service;

import java.util.List;





import com.fourTL.entities.Order_data;




public interface OrdersService {
	
	List<Order_data> findAll();

	Order_data findById(Long id);
	

}
