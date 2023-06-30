package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Role;

public interface RoleDAO extends JpaRepository<Role, String>{

}
