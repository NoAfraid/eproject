package com.eproject.controller;

import com.eproject.common.R;
import com.eproject.entity.Comment;
import com.eproject.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 添加评论
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/add",produces = "application/json;charset=UTF-8")
    public R addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
        return R.ok();
    }

    /**
     * 删除评论
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/delete",produces = "application/json;charset=UTF-8")
    public R deleteComment(@RequestBody Map<String,Object> params){
        int num = commentService.deleteComment(params);
        if (num > 0){
            return R.ok("删除成功");
        }
        return R.error(-1,"删除失败");
    }
    /**
     * 显示评论
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/commentList",produces = "application/json;charset=UTF-8")
    public R commentList(@RequestParam("userId") Integer userId){
        List<Comment> list = commentService.selectCommentList(userId);
        String img = commentService.selectImg(userId);
        if (list.size() > 0){
//            String nick = list.get(0).getMemberNickName();
//            System.out.println(nick);
            return R.ok().put("data",list).put("pics",img);
        }
        return R.error(-1,"查询失败");
    }
}
