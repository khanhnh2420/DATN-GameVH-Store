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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
public class Products  implements Serializable{
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	String poster;
	String thumbnail;
	Double originPrice;
	Double salePrice;
	Double offer;
	String source;
	String link;
	String details;
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createDate = new Date();
	Boolean available;
	
	@ManyToOne
	@JoinColumn(name = "Categoryid")
	Categories category;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<OrderDetails> orderDetails;	
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Favorites> favorites;	
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<FeedBacks> feedBacks;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Banners> banners;	
}
