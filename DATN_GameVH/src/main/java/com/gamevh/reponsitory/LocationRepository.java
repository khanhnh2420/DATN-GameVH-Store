package com.gamevh.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamevh.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer>{

}
