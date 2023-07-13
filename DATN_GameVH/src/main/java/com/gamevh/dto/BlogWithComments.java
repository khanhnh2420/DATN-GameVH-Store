package com.gamevh.dto;

import java.time.LocalDate;
import java.util.List;

import com.gamevh.entities.Comment;

public interface BlogWithComments {
	Integer getBlogId();
	String getTittle();
	String getContent();
	String getImage();
	LocalDate getCreateDate();
	Boolean getStatus();
	List<Comment> getComment();
}
