package com.gamevh.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gamevh.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    
}
