package com.fourTL.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="category")
public class Category implements Serializable{
	@Id
    @Column(name="Id", unique=true, nullable=false, length=4)
    private String id;
    @Column(name="Name", length=255)
    private String name;
    @OneToMany(mappedBy="category")
    private List<Product> product;
}
