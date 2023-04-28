package com.fourTL.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
public class Accounts  implements Serializable{
	@Id
	String username;
	String password;
	String fullname;
	String email;
	String address;
	String photo;
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Orders> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	List<Authorities> authorities;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Blogs> blogs;

	@OneToMany(mappedBy = "account")
	 List<Comments> comments;

}
