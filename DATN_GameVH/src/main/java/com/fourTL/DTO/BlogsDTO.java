package com.fourTL.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
