package com.fourTL.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
public class Accessory implements Serializable{
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	String poster;
	String thumbnail;
	Double originPrice;
	Double salePrice;
	Double offer;
	String supplier;
	Integer qty;
	String Details;
	@Temporal(TemporalType.DATE)
	@Column(name = "CreateDate")
	Date createDate = new Date();
	Boolean available;
	@JsonIgnore
	@OneToMany(mappedBy = "accessory")
	List<Favorite> favorites;	
	
	@JsonIgnore
	@OneToMany(mappedBy = "accessory")
	List<FeedBack> feedBacks;
	
	@JsonIgnore
	@OneToMany(mappedBy = "accessory")
	List<OrderDetail> orderDetails;
}
