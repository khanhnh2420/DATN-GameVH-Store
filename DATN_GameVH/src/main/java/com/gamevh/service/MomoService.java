package com.gamevh.service;

import com.gamevh.dto.MomoDTO;
import com.gamevh.dto.MomoResultDTO;

public interface MomoService {
	MomoResultDTO createOrder(MomoDTO momoDTO);
}
