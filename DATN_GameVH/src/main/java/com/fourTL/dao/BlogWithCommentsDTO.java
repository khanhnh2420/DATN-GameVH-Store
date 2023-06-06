package com.fourTL.dao;

import com.fourTL.entities.Blog;
import com.fourTL.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class BlogWithCommentsDTO {
    private Blog blog;
    private List<Comment> comments;
    public BlogWithCommentsDTO(Blog blog, List<Comment> comments) {
        this.blog = blog;
        this.comments = comments;
    }
}
