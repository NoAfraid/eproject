package com.eproject.controller;

import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.service.UserService;
import com.eproject.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(method= RequestMethod.POST, value = "/adminUser",produces = "application/json;charset=UTF-8")
public class AdminUserController {
    @Resource
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping(value = "/users/list", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public R list(@RequestBody Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return R.error(-1,"参数异常！");
        }
        PageQuery pageUtil = new PageQuery(params);
        return R.ok().put("data",userService.usersPage(pageUtil));
    }

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     */
    @RequestMapping(value = "/users/lock/{disableStatus}", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public R delete(@RequestBody Integer[] ids, @PathVariable int disableStatus) {
        if (ids.length < 1) {
            return R.error("参数异常！");
        }
        if (userService.disableUsers(ids, disableStatus)) {
            return R.ok("启用成功");
        }
        return R.error("操作失败");
    }

}
