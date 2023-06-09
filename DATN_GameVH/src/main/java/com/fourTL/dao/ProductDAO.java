package com.fourTL.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fourTL.DTO.ProductDTO;
import com.fourTL.entities.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {

	// Tìm sản phẩm theo CategoryID
	List<Product> findByCategoryId(String categoryId);

	// Tìm sản phẩm theo search
	List<Product> findByNameContaining(String search);

	long countByCategoryId(String categoryId);

	// Tìm sản phẩm theo Categories
	@Query("SELECT p FROM Product p WHERE p.category.id = ?1")
	Page<Product> findByCategoryId(String categoryId, Pageable pageable);

	// Tìm sản phẩm theo Source
	@Query("SELECT p FROM Product p WHERE p.source = ?1")
	Page<Product> findBySource(String source, Pageable pageable);

	@Query(value = "SELECT p.id, p.name, p.poster, p.thumbnail, p.salePrice, p.offer, p.details, p.createDate, subquery.rate, subquery.countFeedBack, categoryTemp.categoryId, categoryTemp.categoryName "
			+ "FROM Product p " + "INNER JOIN ( "
			+ "    SELECT f.productId AS productId, AVG(f.star) AS rate, COUNT(f.productId) AS countFeedBack "
			+ "    FROM FeedBack f " + "    WHERE f.productId IS NOT NULL " + "    GROUP BY f.productId "
			+ "    HAVING AVG(f.star) IS NOT NULL "
			+ ") subquery ON p.id = subquery.productId " + "INNER JOIN ( "
			+ "    SELECT id AS categoryId, name AS categoryName " + "    FROM Category "
			+ ") categoryTemp ON p.categoryId = categoryTemp.categoryId", nativeQuery = true)
	List<ProductDTO> findTopRatedProducts();
	
	@Query(value = "SELECT p.id, p.name, p.poster, p.thumbnail, p.salePrice, p.offer, p.details, p.createDate, subquery.rate, subquery.countFeedBack, categoryTemp.categoryId, categoryTemp.categoryName "
			+ "FROM Product p " + "INNER JOIN ( "
			+ "    SELECT f.productId AS productId, AVG(f.star) AS rate, COUNT(f.productId) AS countFeedBack "
			+ "    FROM FeedBack f " + "    WHERE f.productId IS NOT NULL " + "    GROUP BY f.productId "
			+ "    HAVING AVG(f.star) IS NOT NULL "
			+ ") subquery ON p.id = subquery.productId " + "INNER JOIN ( "
			+ "    SELECT id AS categoryId, name AS categoryName " + "    FROM Category "
			+ ") categoryTemp ON p.categoryId = categoryTemp.categoryId", countQuery = "SELECT COUNT(*) FROM Product", nativeQuery = true)
	Page<ProductDTO> findAllProductDTO(Pageable pageable);
}
