package com.fourTL.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer id_comment;
    private String content_comment;
    Date createDate_comment = new Date();
    private String Username_comment;
    //private Integer id_blog;
    //private String fullname_account;

}
