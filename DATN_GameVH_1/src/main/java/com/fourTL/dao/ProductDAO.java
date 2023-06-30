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

	@Query(value = "SELECT p.id, p.name, p.poster, p.thumbnail, p.sale_price, p.offer, p.details, p.create_date, subquery.rate, subquery.countFeedBack, categoryTemp.categoryId, categoryTemp.categoryName "
			+ "FROM Product p " + "INNER JOIN ( "
			+ "    SELECT f.product_id AS productId, AVG(f.star) AS rate, COUNT(f.product_id) AS countFeedBack "
			+ "    FROM FeedBack f " + "    WHERE f.product_id IS NOT NULL AND f.status = true" + "    GROUP BY f.product_id "
			+ "    HAVING AVG(f.star) IS NOT NULL "
			+ ") subquery ON p.id = subquery.product_id " + "INNER JOIN ( "
			+ "    SELECT id AS categoryId, name AS categoryName " + "    FROM Category "
			+ ") categoryTemp ON p.category_id = categoryTemp.id", nativeQuery = true)
	List<ProductDTO> findProductFeedBack();
}
