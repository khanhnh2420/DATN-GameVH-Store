package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Favorites;

public interface FavoritesDAO extends JpaRepository<Favorites, Integer>{

}
