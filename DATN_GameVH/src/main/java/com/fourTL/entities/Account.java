package com.fourTL.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name="account", indexes={@Index(name="account_Username_IX", columnList="Username", unique=true), @Index(name="account_Email_IX", columnList="Email", unique=true), @Index(name="account_Photo_IX", columnList="Photo", unique=true)})
public class Account  implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="Username", unique=true, nullable=false, length=50)
    private String username;
    @Column(name="Password", length=255)
    private String password;
    @Column(name="Fullname", length=255)
    private String fullname;
    @Column(name="Email", unique=true, length=255)
    private String email;
    @Column(name="Photo", unique=true, length=255)
    private String photo;
    @Column(name="Status", nullable=false, length=1)
    private boolean status;
    @OneToMany(mappedBy="account")
    private List<Blog> blog;
    @OneToMany(mappedBy="account")
    private List<Comment> comment;
    @OneToMany(mappedBy="account")
    private List<CouponOwner> couponOwner;
    @OneToMany(mappedBy="account")
    private List<Favorite> favorite;
    @OneToMany(mappedBy="account")
    private List<Feedback> feedback;
    @OneToMany(mappedBy="account")
    private List<Location> location;
    @OneToMany(mappedBy="account")
    private List<OrderData> orderData;
    @OneToMany(mappedBy="account")
    private List<Authority> authority;
}
