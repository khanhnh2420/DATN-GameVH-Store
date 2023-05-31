package com.fourTL.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
public class Coupons implements Serializable{
	@Id	
	String code;
	String couponName;
	Integer amount;
	Double value;
	Double minSpend;
	String description;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MfgDate")
	Date mfgDate = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ExpDate")
	Date expDate = new Date();
	Boolean status;
	
	@JsonIgnore
	@OneToMany(mappedBy = "coupon")
	List<Coupon_owners> couponOwners;
}
