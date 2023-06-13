package com.fourTL.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity(name="authority")
public class Authority  implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=10)
    private int id;
    @ManyToOne(optional=false)
    @JoinColumn(name="RoleId", nullable=false)
    private Role role;
    @ManyToOne(optional=false)
    @JoinColumn(name="Username", nullable=false)
    private Account account;
}