package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Category;


public interface CategoryDAO extends JpaRepository<Category, String> {
    
}
