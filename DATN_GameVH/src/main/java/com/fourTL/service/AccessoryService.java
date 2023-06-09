package com.fourTL.service;

import java.util.List;

import com.fourTL.DTO.AccessoryDTO;
import com.fourTL.entities.Accessory;

public interface AccessoryService {

	List<Accessory> findAll();

	Accessory findById(Integer id);
	
	List<AccessoryDTO> findAccessoryFeedBack();
}
