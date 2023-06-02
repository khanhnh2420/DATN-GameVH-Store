package com.fourTL.dao;

import com.fourTL.entities.Blogs;
import com.fourTL.entities.Comments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class BlogWithCommentsDTO {
    private Blogs blog;
    private List<Comments> comments;
    public BlogWithCommentsDTO(Blogs blog, List<Comments> comments) {
        this.blog = blog;
        this.comments = comments;
    }
}
