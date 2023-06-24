package com.fourTL.entities;

import java.io.Serializable;
import java.time.LocalDate;
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
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name="blog", indexes={@Index(name="blog_Tittle_IX", columnList="Tittle", unique=true)})
public class Blog implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=10)
    private Integer id;

    @Column(name="Tittle", unique=true, nullable=false, length=255)
    private String tittle;

    @Column(name="Content", nullable=false)
    private String content;

    @Column(name="create_date", nullable=false)
    private LocalDate createDate;

    @Column(name="Status", nullable=false, length=1)
    private Boolean status;

    @ManyToOne(optional=false)
    @JoinColumn(name="account_id", nullable=false)
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy="blog")
    private List<Comment> comment;

}
