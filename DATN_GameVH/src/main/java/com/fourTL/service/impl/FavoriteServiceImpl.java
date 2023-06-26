package com.fourTL.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fourTL.dao.FavoriteDAO;
import com.fourTL.entities.Favorite;

import com.fourTL.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	@Autowired
	private FavoriteDAO favoriteDAO;
	
	@Override
	public List<Favorite> findAll() {
		return favoriteDAO.findAll();
	}

	@Override
	public Favorite findById(Integer id) {
		return favoriteDAO.findById(id).get();
	}
	
	 public Favorite save(Favorite favorite) {
	        return favoriteDAO.save(favorite);
	    }

	@Override
	public List<Favorite> findAllByAccountId(Integer accountId) {
		// TODO Auto-generated method stub
		return favoriteDAO.findByAccountId(accountId);
	}

	@Override
	public List<Favorite> findByProductIdAndAccountId(Integer accountid, Integer productid) {
		// TODO Auto-generated method stub
		return favoriteDAO.findByProductIdAndAccountId(accountid, productid);
	}

	

	

	
	

	
}
