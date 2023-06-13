package com.fourTL.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fourTL.entities.Orderdetail;

public interface OrderDetailDAO extends JpaRepository<Orderdetail, Long>{
	
	@Query("SELECT ordt FROM OrderDetail ordt GROUP BY ordt ORDER BY COUNT(ordt) DESC LIMIT 6")
	List<Orderdetail> findTopSellingProducts();

}
