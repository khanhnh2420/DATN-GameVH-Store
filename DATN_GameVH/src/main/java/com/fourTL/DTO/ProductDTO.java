package com.fourTL.DTO;

import java.time.LocalDate;

public interface ProductDTO{
	Integer getId();
	String getName();
	String getPoster();
	String getThumbnail();
	Double getSalePrice();
	Double getOffer();
	String getDetails();
	Double getRate();
	Integer getCountFeedBack();
	String getCategoryName();
	Integer getCategoryId();
	LocalDate getCreateDate();
}
