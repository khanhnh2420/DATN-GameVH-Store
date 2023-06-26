package com.fourTL.DTO.impl;

import java.time.LocalDate;

import com.fourTL.DTO.ProductDTO;

public class ProductDTOImpl implements ProductDTO {
	private Integer id;
	private String name;
	private String poster;
	private String thumbnail;
	private Double salePrice;
	private Double offer;
	private String details;
	private Double rate;
	private Integer countFeedBack;
	private String categoryName;
	private Integer categoryId;
	private LocalDate createDate;

	// Constructor
	public ProductDTOImpl(Integer id, String name, String poster, String thumbnail, Double salePrice, Double offer,
			String details, Double rate, Integer countFeedBack, String categoryName, Integer categoryId,
			LocalDate localDate) {
		this.id = id;
		this.name = name;
		this.poster = poster;
		this.thumbnail = thumbnail;
		this.salePrice = salePrice;
		this.offer = offer;
		this.details = details;
		this.rate = rate;
		this.countFeedBack = countFeedBack;
		this.categoryName = categoryName;
		this.categoryId = categoryId;
		this.createDate = localDate;
	}

	// Getter methods
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPoster() {
		return poster;
	}

	@Override
	public String getThumbnail() {
		return thumbnail;
	}

	@Override
	public Double getSalePrice() {
		return salePrice;
	}

	@Override
	public Double getOffer() {
		return offer;
	}

	@Override
	public String getDetails() {
		return details;
	}

	@Override
	public Double getRate() {
		return rate;
	}

	@Override
	public Integer getCountFeedBack() {
		return countFeedBack;
	}

	@Override
	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public Integer getCategoryId() {
		return categoryId;
	}

	@Override
	public LocalDate getCreateDate() {
		return createDate;
	}
}
