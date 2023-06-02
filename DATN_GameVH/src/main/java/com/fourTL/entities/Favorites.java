package com.fourTL.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
public class Favorites implements Serializable{
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Temporal(TemporalType.DATE)
	@Column(name = "LikeDate")
	Date likeDate = new Date();
	Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "Username")
	Accounts account;
	
	@ManyToOne
	@JoinColumn(name = "ProductId")
	Products product;
	
	@ManyToOne
	@JoinColumn(name = "AccessoriesId")
	Accessories accessory;
}
