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
public class Comment implements Serializable {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String content;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateDate")
	Date createDate = new Date();
	Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "Username")
	Account account;
	
	@ManyToOne
	@JoinColumn(name = "BlogId")
	Blog blog;
}
