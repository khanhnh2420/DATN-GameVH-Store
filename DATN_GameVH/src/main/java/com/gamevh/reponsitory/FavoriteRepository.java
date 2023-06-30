package com.gamevh.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamevh.entities.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{

}
