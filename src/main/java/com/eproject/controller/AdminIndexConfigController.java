package com.eproject.controller;

import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.common.ServiceResultEnum;
import com.eproject.entity.IndexConfig;
import com.eproject.service.AdminIndexConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(method= RequestMethod.POST, value = "/adminIndexConfig",produces = "application/json;charset=UTF-8")
public class AdminIndexConfigController {

    @Resource
    private AdminIndexConfigService adminIndexConfigService;

    /**
     * 添加
     */
    @RequestMapping(value = "/indexConfigs/save", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public R save(@RequestBody IndexConfig indexConfig) {
        if (Objects.isNull(indexConfig.getConfigType())
                || StringUtils.isEmpty(indexConfig.getConfigName())
                || Objects.isNull(indexConfig.getConfigRank())) {
            return R.error("参数异常！");
        }
        String result = adminIndexConfigService.saveIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return R.ok().put("date","添加成功");
        } else {
            return R.error(result);
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/indexConfigs/delete", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public R delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return R.error("参数异常！");
        }
        if (adminIndexConfigService.deleteBatch(ids)) {
            return R.ok("添加成功");
        } else {
            return R.error("删除失败");
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/indexConfigs/update", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public R update(@RequestBody IndexConfig indexConfig) {
        if (Objects.isNull(indexConfig.getConfigType())
                || Objects.isNull(indexConfig.getId())
                || StringUtils.isEmpty(indexConfig.getConfigName())
                || Objects.isNull(indexConfig.getConfigRank())) {
            return R.error("参数异常！");
        }
        String result = adminIndexConfigService.updateIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return R.ok("修改成功");
        } else {
            return R.error("修改失败");
        }
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/indexConfigs/list",  method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public R list(@RequestBody Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return R.error("参数异常！");
        }
        PageQuery pageUtil = new PageQuery(params);
        return R.ok().put("data",adminIndexConfigService.getConfigsPage(pageUtil));
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/indexConfigs/info/{id}", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public R info(@PathVariable("id") Integer id) {
        IndexConfig config = adminIndexConfigService.getIndexConfigById(id);
        if (config == null) {
            return R.error("未查询到数据");
        }
        return R.ok().put("data",config);
    }

    /**
     * 根据id获取信息返回给前端修改
     */
    @RequestMapping(value = "/getIndexConfig",  method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public R getIndexConfig(@RequestBody IndexConfig indexConfig){
        IndexConfig config = adminIndexConfigService.getIndexConfigById(indexConfig.getId());
        return R.ok().put("data",config);
    }
}
