package com.gamevh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


import com.gamevh.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	 @Query("SELECT p FROM Product p " +
	           "WHERE (:productName IS NULL OR p.name LIKE %:productName%) " +
	           "AND (:productType IS NULL OR p.type LIKE %:productType%) " +
	           "AND (:categoryName IS NULL OR p.category.name LIKE %:categoryName%)")
	    List<Product> searchProducts(@Param("productName") String productName,
	                                 @Param("productType") String productType,
	                                 @Param("categoryName") String categoryName);
    
}
