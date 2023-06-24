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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="comment")
public class Comment implements Serializable {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=10)
    private Integer id;

    @Column(name="AccountId", nullable=false, length=255)
    private String AccountId;
    @Column(name="Content", nullable=false, length=255)
    private String content;
    @Column(name="Content", nullable=false, length=255)
    private String BlogId;
    
    @Temporal(TemporalType.DATE)
    @Column(name="CreateDate", nullable=false)
    private Date createDate;
    
    @Column(name="Status", nullable=false, length=1)
    private Boolean status;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="AccountId", nullable=false)
    private Account account;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="BlogId", nullable=false)
    private Blog blog;
}
