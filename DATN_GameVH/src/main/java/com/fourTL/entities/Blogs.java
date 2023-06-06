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
public class Blogs implements Serializable{
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String tittle;
	String content;
	@Temporal(TemporalType.DATE)
	@Column(name = "CreateDate")
	Date createDate = new Date();
	Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "Username")
	Accounts account;
	
	@JsonIgnore
	@OneToMany(mappedBy = "blogs")
	List<Comments> comments;
}
