package com.gamevh.service.impl;



import com.gamevh.dto.CommentDTO1;
import com.gamevh.entities.Comment;
import com.gamevh.reponsitory.CommentRepository;
import com.gamevh.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentService {
    @Autowired
    CommentRepository cmtDao;





//    @Query("SELECT MAX(ThoiGianCmt) AS LatestDate FROM Comments ")
//    public List<Comments> findNewsestComment() {
//        return cmtDao.findNewsestComment();
//    }

    @Override
    public List<Comment> findAll() {
        return cmtDao.findAll();
    }

//  @Override
//    public List<Comment> findAllByIdBlog(Integer id){
//        return cmtDao.findAllByIdBlog(id);
//    }

    public List<Comment> findAllById(Iterable<Integer> integers) {
        return cmtDao.findAllById(integers);
    }

    @Override
    public  List<Comment> findAllCommentAndBlogByIdBlog(Integer blogId){
        return cmtDao.findAllCommentAndBlogByIdBlog(blogId);
    }
   /* @Override
    public CommentDTO createCMT(CommentDTO commentdto){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment cmt = new Comment();
        cmt.setId(commentdto.getId_comment());
      //  cmt.setBlog(commentdto.getId_blog());
        cmt.setAccount(commentdto.setFullname_account());
        cmt.setContent(commentdto.getContent_comment());
        cmt.setCreateDate(timestamp);
        cmt.setStatus(true);

        Account account= acountService.findById(commentdto.getUsername_comment());


        return cmtDao.save(commentdto);
    }*/

    @Override
    public Comment save(CommentDTO1 dto) {
        Comment cmt = new Comment();
        BeanUtils.copyProperties(dto, cmt);
        cmtDao.save(cmt);
        return cmt;
    }
}
