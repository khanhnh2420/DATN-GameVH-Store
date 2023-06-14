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
@Table(name="feedback")
public class Feedback implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="Content", nullable=false, length=255)
    private String content;
    @Column(name="Status", nullable=false, length=1)
    private boolean status;
    @Column(name="Star", nullable=false, precision=10)
    private int star;
    @Column(name="CreateDate", nullable=false)
    private Date createDate;
    @ManyToOne
    @JoinColumn(name="AccessoryId")
    private Accessory accessory;
    @ManyToOne(optional=false)
    @JoinColumn(name="Username", nullable=false)
    private Account account;
    @ManyToOne
    @JoinColumn(name="ProductId")
    private Product product;
}
