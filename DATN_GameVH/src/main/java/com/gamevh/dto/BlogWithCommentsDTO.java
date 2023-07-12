package com.gamevh.dto;


import com.gamevh.entities.Blog;
import com.gamevh.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogWithCommentsDTO {
    private BlogsDTO blogsDTO;
    private List<CommentDTO> commentDTOS;

    public BlogWithCommentsDTO(Blog blog, List<Comment> comments) {
        this.blogsDTO = new BlogsDTO(blog.getId(),blog.getTittle(),blog.getContent(),
                blog.getCreateDate(),blog.getAccount().getFullname());
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getStatus()==true) {
                CommentDTO commentDTO = new CommentDTO(comment.getId(), comment.getContent(),
                        comment.getCreateDate(), comment.getAccount().getFullname());
                commentDTOs.add(commentDTO);
            }
        }
        // đảo thứ tự
//        Collections.sort(comments, Comparator.comparingLong(Comment::getId));
//        Collections.reverse(comments);
        this.commentDTOS = commentDTOs;

    }

}
