package com.fourTL.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name="blog", indexes={@Index(name="blog_Tittle_IX", columnList="Tittle", unique=true)})
public class Blog implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="Tittle", unique=true, nullable=false, length=255)
    private String tittle;
    @Column(name="Content", nullable=false)
    private String content;
    @Column(name="CreateDate", nullable=false)
    private Date createDate;
    @Column(name="Status", nullable=false, length=1)
    private boolean status;
    @ManyToOne(optional=false)
    @JoinColumn(name="Username", nullable=false)
    private Account account;
    @OneToMany(mappedBy="blog")
    private List<Comment> comment;
}
