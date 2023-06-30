package com.gamevh.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamevh.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

}
