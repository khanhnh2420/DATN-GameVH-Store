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
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name="accessory", indexes={@Index(name="accessory_Name_IX", columnList="Name", unique=true), @Index(name="accessory_Poster_IX", columnList="Poster", unique=true), @Index(name="accessory_Thumbnail_IX", columnList="Thumbnail", unique=true)})
public class Accessory implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="Name", unique=true, nullable=false, length=50)
    private String name;
    @Column(name="Poster", unique=true, length=50)
    private String poster;
    @Column(name="Thumbnail", unique=true, length=255)
    private String thumbnail;
    @Column(name="OriginPrice", nullable=false, precision=22)
    private double originPrice;
    @Column(name="SalePrice", nullable=false, precision=22)
    private double salePrice;
    @Column(name="Offer", nullable=false, precision=22)
    private double offer;
    @Temporal(TemporalType.DATE)
    @Column(name="CreateDate", nullable=false)
    private Date createDate;
    @Column(name="Available", nullable=false, length=1)
    private boolean available;
    @Column(name="Supplier", nullable=false, length=255)
    private String supplier;
    @Column(name="Qty", nullable=false, precision=10)
    private int qty;
    @Column(name="Details", nullable=false, length=500)
    private String details;
    @Column(name="Status", nullable=false, length=1)
    private boolean status;
    @JsonIgnore
    @OneToMany(mappedBy="accessory")
    private List<Favorite> favorite;
    @JsonIgnore
    @OneToMany(mappedBy="accessory")
    private List<Feedback> feedback;
    @JsonIgnore
    @OneToMany(mappedBy="accessory")
    private List<Orderdetail> orderdetail;
}
