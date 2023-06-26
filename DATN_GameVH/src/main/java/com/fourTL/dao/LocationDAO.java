package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Location;

public interface LocationDAO extends JpaRepository<Location, Integer>{

}
