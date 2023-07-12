package com.gamevh.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamevh.entities.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByCategoryId(String categoryId);
}
