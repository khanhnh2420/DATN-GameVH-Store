package com.gamevh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogsDTO {
    private Integer id;
    private String tittle;
    private String content;
    LocalDate createDate;
    private String Username;
    //????

}
