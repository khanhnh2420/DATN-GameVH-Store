package com.fourTL.DTO.impl;

import java.util.Date;

import com.fourTL.DTO.AccessoryDTO;

import lombok.Setter;

public class AccessoryDTOImpl implements AccessoryDTO {
    private Integer id;
    private String name;
    private String poster;
    private String thumbnail;
    private Double salePrice;
    private Double offer;
    private String details;
    private Double rate;
    private Integer countFeedBack;
    private Date createDate;

    public AccessoryDTOImpl(Integer id, String name, String poster, String thumbnail, Double salePrice, Double offer, String details, Double rate, Integer countFeedBack, Date createDate) {
        this.id = id;
        this.name = name;
        this.poster = poster;
        this.thumbnail = thumbnail;
        this.salePrice = salePrice;
        this.offer = offer;
        this.details = details;
        this.rate = rate;
        this.countFeedBack = countFeedBack;
        this.createDate = createDate;
    }

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
    public Date getCreateDate() {
        return createDate;
    }
}
