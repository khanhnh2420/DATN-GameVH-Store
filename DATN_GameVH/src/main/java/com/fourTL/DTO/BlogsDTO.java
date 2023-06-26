package com.fourTL.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogsDTO {
    private Integer id;
    private String tittle;
    private String content;
    Date createDate = new Date();
    private String Username;
    //????

}
