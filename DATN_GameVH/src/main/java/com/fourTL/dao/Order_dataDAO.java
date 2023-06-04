package com.fourTL.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fourTL.entities.Order_data;






public interface Order_dataDAO extends JpaRepository<Order_data, Long>{
	
	 @Query("SELECT o FROM Order_data o WHERE o.account.username = ?1")
	 	List<Order_data> findByUsernameContaining(String search);
	
}
