package com.fourTL.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
