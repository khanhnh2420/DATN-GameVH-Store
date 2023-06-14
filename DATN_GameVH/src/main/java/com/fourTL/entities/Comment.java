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
import jakarta.persistence.Table;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name="comment")
public class Comment implements Serializable {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="Content", nullable=false, length=255)
    private String content;
    @Column(name="CreateDate", nullable=false)
    private Date createDate;
    @Column(name="Status", nullable=false, length=1)
    private boolean status;
    @ManyToOne(optional=false)
    @JoinColumn(name="Username", nullable=false)
    private Account account;
    @ManyToOne(optional=false)
    @JoinColumn(name="BlogId", nullable=false)
    private Blog blog;
}
