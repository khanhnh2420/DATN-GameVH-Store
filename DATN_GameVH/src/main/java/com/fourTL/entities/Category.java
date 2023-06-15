package com.fourTL.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="category")
public class Category implements Serializable{
	@Id
    @Column(name="Id", unique=true, nullable=false, length=4)
    private String id;
	
    @Column(name="Name", length=255)
    private String name;
    
    @JsonIgnore
    @OneToMany(mappedBy="category")
    private List<Product> product;
}
