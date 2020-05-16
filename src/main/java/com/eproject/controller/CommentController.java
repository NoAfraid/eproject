package com.eproject.controller;

import com.eproject.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

}
