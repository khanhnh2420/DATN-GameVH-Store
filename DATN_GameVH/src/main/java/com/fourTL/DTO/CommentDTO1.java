package com.fourTL.DTO;

import com.fourTL.entities.Account;
import com.fourTL.entities.Blog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO1 implements Serializable {


    private String content;

    private Date createDate;


    private Boolean status;


    private String AccountId;


    private String BlogId;

    /*Integer BlogId;
    Integer AccountId ;
    String content;
    Date createDate;
    boolean status;*/

}
