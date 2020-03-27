package com.eproject.controller;

import com.eproject.common.Contants;
import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.entity.Carouse;
import com.eproject.service.CarouseService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/carouse",produces = "application/json;charset=UTF-8")
public class CarouseController {

    @Resource
    private CarouseService carouseService;

    /**
     * 添加轮播
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/add",produces = "application/json;charset=UTF-8")
    public R addCarouse(@RequestBody Carouse carouse){
        int count = carouseService.addCarouse(carouse);
        if (count > 0){
            return R.ok("添加成功");
        }
        return R.error(-1,"添加失败");
    }

    /**
     * 获取轮播列表
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getCarouseList",produces = "application/json;charset=UTF-8")
    public R getCarouseList(@RequestParam Map<String, Object> result){
        try {
            if (StringUtils.isEmpty(result.get("page")) || StringUtils.isEmpty(result.get("limit"))) {
                return R.error(-1, "请求错误");
            }
            PageQuery pageQuery = new PageQuery(result);
            return R.ok().put("data", carouseService.getCarouseList(pageQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(-405, "查询订单列表失败，msg:" + e.getMessage());
        }
    }

    /**
     * 更新轮播
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/updateCarouse",produces = "application/json;charset=UTF-8")
    public R updateCarouse(@RequestBody Carouse carouse){
        int count = carouseService.updateCarouse(carouse);
        if (count > 0){
            return R.ok("更新成功");
        }
        return R.error(-1,"更新失败");
    }

    /**
     * 批量删除轮播
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/deleteCarouse",produces = "application/json;charset=UTF-8")
    public R deleteCarouse(@RequestParam("ids") List<Integer> ids){
        int count = carouseService.deleteCarouse(ids);
        if (count > 0){
            return R.ok("删除成功");
        }
        return R.error(-1,"删除失败");
    }

    /**
     * 根据轮播ID获取轮播
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getCarouseById",produces = "application/json;charset=UTF-8")
    public R getCarouseById(@RequestParam("id") Integer id){
        Carouse carouse = carouseService.getCarouseById(id);
        if (carouse != null){
            return R.ok().put("data",carouse);
        }
        return R.error(-1,"获取失败");
    }

    /**
     * 返回固定数量的轮播图对象(首页调用)
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getCarouseForIndex",produces = "application/json;charset=UTF-8")
    public R getCarouseForIndex(){
        List<Carouse> carouseList = carouseService.getCarouseForIndex(Contants.INDEX_CAROUSEL_NUMBER);
        if (carouseList.size() > 0){
            return R.ok().put("data",carouseList);
        }
        return R.error(-1,"获取错误");
    }
}
