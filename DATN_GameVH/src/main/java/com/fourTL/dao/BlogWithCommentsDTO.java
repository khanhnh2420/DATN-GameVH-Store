package com.fourTL.dao;

import com.fourTL.entities.Blog;
import com.fourTL.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogWithCommentsDTO {

//    public BlogWithCommentsDTO(Blog blog, List<Comment> comments) {
//        this.blog = blog;
//        this.comments = comments;
//    }
  private Integer id;
   private String content;
   private  String Username;
   private Date createDate = new Date();

   private Integer BlogId;
    private String content_blog;
    private  String Username_blog;
    private Date createDate_blog = new Date();
    private String tittle_blog;





//    @Query(value = "SELECT * FROM comment c INNER JOIN blog b ON c.BlogId = b.id WHERE b.Id = ?1", nativeQuery = true)
//    List<Comment> findCommentsByBlogId(int blogId);
}
