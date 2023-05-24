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
@Table(name = "Comments")
public class Comments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;

    String NoiDungCmt;
    @Temporal(TemporalType.DATE)
    @Column(name = "ThoiGianCmt")
    Date ThoiGianCmt = new Date();

    @ManyToOne
    @JoinColumn(name = "IdBlog")
    Blogs blogs;

    @ManyToOne
    @JoinColumn(name = "Username")
    Accounts account;
}
