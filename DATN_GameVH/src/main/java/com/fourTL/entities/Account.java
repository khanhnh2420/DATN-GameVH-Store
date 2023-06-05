package com.fourTL.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
public class Account  implements Serializable{
	@Id
	String username;
	String password;
	String fullname;
	String email;
	String address;
	String photo;
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order_data> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	List<Authority> authorities;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Coupon_owner> couponOwners;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Favorite> favorites;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Comment> comments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<FeedBack> feedBacks;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Blog> blogs;
}
