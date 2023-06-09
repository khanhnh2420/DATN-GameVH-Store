package com.fourTL.DTO;

import java.util.Date;

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
	String getCategoryId();
	Date getCreateDate();
}
