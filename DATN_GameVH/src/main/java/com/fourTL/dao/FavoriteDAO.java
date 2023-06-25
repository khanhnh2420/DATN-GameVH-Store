package com.fourTL.dao;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fourTL.entities.Favorite;



public interface FavoriteDAO extends JpaRepository<Favorite, Integer>{
	List<Favorite> findByAccountId(Integer AccountId);
	
	 @Query("SELECT f FROM Favorite f WHERE f.account.id = ?1 and f.product.id = ?2")
	 	List<Favorite> findByProductIdAndAccountId( Integer  accountid, Integer productid);
	 
}
