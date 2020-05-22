package com.eproject.service.serverIm;

import com.eproject.dao.CommentDao;
import com.eproject.entity.Comment;
import com.eproject.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceIm implements CommentService {

    @Resource
    private CommentDao commentDao;

    @Override
    public void addComment(Comment comment){
        comment.setCreateTime(new Date());
        commentDao.insertSelective(comment);
    }

    @Override
    public int deleteComment(Map<String,Object> params){
        Comment c = new Comment();
        c.setOrderId(Integer.parseInt(params.get("orderId")+""));
        c.setUserId(Integer.parseInt(params.get("userId")+""));
        c.setProductId(Integer.parseInt(params.get("productId")+""));
        if (commentDao.deleteComment(c) > 0){
            return 1;
        }
        return 0;
    }

    @Override
    public List<Comment> selectCommentList(Integer userId){
        List<Comment> commentList = commentDao.selectCommentList(userId);
        if (commentList.size() > 0){
            return commentList;
        }
        return null;
    }

    @Override
    public String selectImg(Integer userId){
        String img = commentDao.selectImg(userId);
        return img;
    }
}
