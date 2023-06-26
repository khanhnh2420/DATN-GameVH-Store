package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Favorite;

public interface FavoriteDAO extends JpaRepository<Favorite, Integer>{

}
