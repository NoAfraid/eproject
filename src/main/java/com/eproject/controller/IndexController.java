package com.eproject.controller;

import com.eproject.common.Contants;
import com.eproject.common.IndexConfigTypeEnum;
import com.eproject.common.R;
import com.eproject.entity.Carouse;
import com.eproject.entity.Product;
import com.eproject.service.CarouseService;
import com.eproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/index",produces = "application/json;charset=UTF-8")
public class IndexController {

    @Resource
    private CarouseService carouseService;

    @Resource
    private ProductService productService;

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

    /**
     * 返回固定数量的新上架的产品对象(首页调用)
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getProductForIndex",produces = "application/json;charset=UTF-8")
    public R getProductForIndex(){
        List<Product> newProductList = productService.getProductForIndex(Contants.INDEX_GOODS_NEW_NUMBER);
        if (newProductList.size() > 0){
            return R.ok().put("data",newProductList);
        }
        return R.error(-1,"获取错误");
    }

    /**
     * 返回固定数量的销量多的产品对象(首页调用)
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getProductSaleForIndex",produces = "application/json;charset=UTF-8")
    public R getProductSaleForIndex(){
        List<Product> newProductList = productService.getProductSaleForIndex(Contants.INDEX_GOODS_RECOMMOND_NUMBER);
        if (newProductList.size() > 0){
            return R.ok().put("data",newProductList);
        }
        return R.error(-1,"获取错误");
    }
}
