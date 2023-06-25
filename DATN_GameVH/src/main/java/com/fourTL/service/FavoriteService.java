package com.fourTL.service;

import java.util.List;



import com.fourTL.entities.Favorite;

public interface FavoriteService {
	
	List<Favorite> findAll();

	Favorite findById(Integer id);

	Favorite save(Favorite favorite);

	List<Favorite> findAllByAccountId(Integer accountId);

	List<Favorite> findByProductIdAndAccountId( Integer  accountid, Integer productid);
	

	
}
