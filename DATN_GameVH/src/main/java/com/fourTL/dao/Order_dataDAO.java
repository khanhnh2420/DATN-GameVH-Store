package com.fourTL.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fourTL.entities.OrderData;






public interface Order_dataDAO extends JpaRepository<OrderData, Long>{
	
	 @Query("SELECT o FROM Order_data o WHERE o.account.username = ?1")
	 	List<OrderData> findByUsernameContaining(String search);
	
}
