package com.fourTL.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Blogs implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String BlogTittle;
    String BlogBody;



    @Temporal(TemporalType.DATE)
    @Column(name = "ThoiGianBlog")
    Date ThoiGianBlog = new Date();

    @ManyToOne
    @JoinColumn(name = "Username")
    Accounts account;
//    @ManyToOne
//    @JoinColumn(name = "IdSp")
//    Products product;

    @JsonIgnore
    @OneToMany(mappedBy = "blogs")
    List<Comments> comments;
}
