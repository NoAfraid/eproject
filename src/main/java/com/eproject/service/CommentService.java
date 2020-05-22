package com.eproject.service;

import com.eproject.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {

    /**
     * 添加评论
     */
    void addComment(Comment comment);

    /**
     * 删除评论
     */
    int deleteComment(Map<String,Object> params);

    /**
     * 显示评论
     */
    List<Comment> selectCommentList(Integer userId);

    String selectImg(Integer userId);
}
