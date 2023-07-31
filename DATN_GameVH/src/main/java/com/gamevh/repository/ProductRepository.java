package com.gamevh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

import com.gamevh.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	 @Query(value = "SELECT p.*, " +
             "(SELECT COUNT(*) FROM Product) AS totalProducts, " +
             "(SELECT COUNT(*) FROM Product WHERE status = 1) AS availableProducts " +
             "FROM Product p", nativeQuery = true)
	 	List<Object[]> reportProduct();

	@Query(value = "SELECT * FROM product order by create_date desc LIMIT 5",nativeQuery = true)
	List<Product> getTop5();
	
	@Query("SELECT p FROM Product p " +
	           "WHERE (:productName IS NULL OR p.name LIKE CONCAT('%', :productName, '%')) " +
	           "AND (:productType IS NULL OR p.type LIKE :productType) " +
	           "AND (:categoryName IS NULL OR p.category.name LIKE :categoryName)")
	    List<Product> searchProduct(@Param("productName") Optional<String> productName,
	                                @Param("productType") Optional<String> productType,
	                                @Param("categoryName") Optional<String> categoryName);
}
