package com.fourTL.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
public class Banner implements Serializable{
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String image1;
	String image2;
	String image3;
	String image4;
	String image5;
	String image6;
	Double offer;
	@ManyToOne
	@JoinColumn(name = "ProductId")
	Product product;
}
