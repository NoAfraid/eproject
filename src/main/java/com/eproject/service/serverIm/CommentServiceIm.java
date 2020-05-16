package com.eproject.service.serverIm;

import com.eproject.dao.CommentDao;
import com.eproject.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentServiceIm implements CommentService {

    @Resource
    private CommentDao commentDao;
}
