package com.gamevh.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamevh.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
