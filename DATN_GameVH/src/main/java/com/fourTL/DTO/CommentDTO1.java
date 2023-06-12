package com.fourTL.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO1 {

    Integer BlogId;
    String Username;
    String content;
    Date createDate;
    boolean status;

}
