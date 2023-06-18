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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name="product", indexes={@Index(name="product_Name_IX", columnList="Name", unique=true), @Index(name="product_Poster_IX", columnList="Poster", unique=true), @Index(name="product_Thumbnail_IX", columnList="Thumbnail", unique=true), @Index(name="product_Link_IX", columnList="Link", unique=true)})
public class Product  implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=10)
    private Integer id;
	
    @Column(name="Name", unique=true, length=255)
    private String name;
    
    @Column(name="Poster", unique=true, length=255)
    private String poster;
    
    @Column(name="Thumbnail", unique=true, length=255)
    private String thumbnail;
    
    @Column(name="OriginPrice", precision=22)
    private Double originPrice;
    
    @Column(name="SalePrice", precision=22)
    private Double salePrice;
    
    @Column(name="Offer", precision=22)
    private Double offer;
    
    @Temporal(TemporalType.DATE)
    @Column(name="CreateDate")
    private Date createDate;
    
    @Column(name="Available", precision=3)
    private Boolean available;
    
    @Column(name="Source", nullable=false, length=255)
    private String source;
    
    @Column(name="Link", unique=true, nullable=false, length=500)
    private String link;
    
    @Column(name="Details", nullable=false, length=500)
    private String details;
    
    @Column(name="Status", nullable=false, length=1)
    private Boolean status;
    
    @JsonIgnore
    @OneToMany(mappedBy="product")
    private List<Favorite> favorite;
    
    @JsonIgnore
    @OneToMany(mappedBy="product")
    private List<Feedback> feedback;
    
    @JsonIgnore
    @OneToMany(mappedBy="product")
    private List<OrderDetail> orderDetail;
    
    @JsonIgnore
    @OneToMany(mappedBy="product")
    private List<Banner> banner;
    
    @ManyToOne
    @JoinColumn(name="CategoryId")
    private Category category;	
}
