package com.eproject.controller;

import com.eproject.common.Contants;
import com.eproject.common.IndexConfigTypeEnum;
import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.domain.IndexConfigParam;
import com.eproject.entity.Carouse;
import com.eproject.entity.HistorySearch;
import com.eproject.entity.Product;
import com.eproject.entity.User;
import com.eproject.service.AdminIndexConfigService;
import com.eproject.service.CarouseService;
import com.eproject.service.HistorySearchService;
import com.eproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/index",produces = "application/json;charset=UTF-8")
public class IndexController {

    @Resource
    private CarouseService carouseService;

    @Resource
    private ProductService productService;

    @Resource
    private HistorySearchService historySearchService;

    @Resource
    private AdminIndexConfigService adminIndexConfigService;

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
        List<Product> saleProductList = productService.getProductSaleForIndex(Contants.INDEX_GOODS_RECOMMOND_NUMBER);
        if (saleProductList.size() > 0){
            return R.ok().put("data",saleProductList);
        }
        return R.error(-1,"获取错误");
    }

    /**
     * 返回固定数量的热搜的产品对象(首页调用)
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getProductForHotSearch",produces = "application/json;charset=UTF-8")
    public R getProductForHotSearch(){
        List<Product> HotSearchProductList = productService.getProductForHotSearch(Contants.INDEX_HOTSEARCH_NUMBER);
        if (HotSearchProductList.size() > 0){
            return R.ok().put("data",HotSearchProductList);
        }
        return R.error(-1,"获取错误");
    }

    @GetMapping(value = "/returnSearchPage")
//    @ResponseBody
    public String reSearchPage(){
        return "search.html";
    }
    /**
     * 搜索商品（搜索框搜索)
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/searchProductForIndex",produces = "application/json;charset=UTF-8")
    public R searchProductForIndex(@RequestBody Map<String, Object> params, HttpServletRequest request){
        String productName = "";
        String description = "";
        String productSn = "";
//        int userId = Integer.parseInt(params.get("userId"));
        //对keyword做去空格处理
        if (params.containsKey("productName") && !StringUtils.isEmpty((params.get("productName") + "").trim())) {
            productName = params.get("productName") + "";
        }
        params.put("productName",productName);

        if (params.containsKey("description") && !StringUtils.isEmpty((params.get("description") + "").trim())) {
            description = params.get("description") + "";
        }
        params.put("description",description);

        if (params.containsKey("productSn") && !StringUtils.isEmpty((params.get("productSn") + "").trim())) {
            productSn = params.get("productSn") + "";
        }
        params.put("productSn",productSn);
        int userId;
        if (params.containsKey("productSn") && !StringUtils.isEmpty((params.get("userId") + "").trim())) {
             userId = Integer.parseInt(params.get("userId") + "");
            params.put("userId",userId);
        }

        //封装商品数据
//        Product product = new Product(params);
        return R.ok().put("data",productService.searchProductForIndex(params));
    }

    /**
     * 历史搜索
     */
    @RequestMapping(method= RequestMethod.POST, value = "/historySearch",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public R historySearch(@RequestBody User user){
        int userId = user.getId();
        List<HistorySearch> HistorySearchProductList = historySearchService.getProductForHistorySearch(Contants.INDEX_HOTSEARCH_NUMBER,userId);
        if (HistorySearchProductList.size() > 0){
            return R.ok().put("data",HistorySearchProductList);
        }
        return R.error(-1,"获取错误");
    }


    /**
     * 热门推荐1、新品上线、热销商品这三者合一
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/indexPage",produces = "application/json;charset=UTF-8")
    public R indexPage(){
        List<IndexConfigParam> hotGoodses = adminIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Contants.INDEX_GOODS_HOT_NUMBER);
        List<IndexConfigParam> newGoodses = adminIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Contants.INDEX_GOODS_NEW_NUMBER);
        List<IndexConfigParam> recommendGoodses = adminIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Contants.INDEX_GOODS_RECOMMOND_NUMBER);
        return R.ok().put("hotGoodses",hotGoodses).put("newGoodses",newGoodses).put("recommendGoodses",recommendGoodses);
    }

    /**
     * 查看更多
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/selectAllProduct",produces = "application/json;charset=UTF-8")
    public R selectAllProduct(){
        List<Product> all = productService.selectAll();
        return R.ok().put("data",all);
    }

    /**
     * 动态排序
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/orderBy",produces = "application/json;charset=UTF-8")
    public R orderByNewProduct(@RequestBody Map<String,Object> o){
        String orderField = o.get("orderField").toString();
        String orderType = o.get("orderType").toString();
        List<Product> all = productService.orderBy(orderField,orderType);
        return R.ok().put("data",all);
    }
}
