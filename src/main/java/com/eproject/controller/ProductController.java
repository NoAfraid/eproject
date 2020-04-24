package com.eproject.controller;

import com.eproject.annotation.LoginUser;
import com.eproject.common.Contants;
import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.entity.Manager;
import com.eproject.entity.Product;
import com.eproject.service.ProductService;
import com.eproject.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 添加商品
     *
     * @param product
     * @return json
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R add(@RequestBody(required = false) Product product, @LoginUser Manager manager) {
        if (manager == null){
            return R.error(-1, "未登录");
        }
        //@RequestParam("productName") String productName
        try {
            if (StringUtils.isEmpty(product.getProductName())) {
                return R.error(-1, "商品名不能为空");
            }
            if (StringUtils.isEmpty(product.getProductImg())) {
                return R.error(-1, "商品图不能为空");
            }
            if (StringUtils.isEmpty(product.getProductSn())) {
                return R.error(-1, "商品编号不能为空");
            }
            if (Objects.isNull(product.getPromotePrice())) {
                return R.error(-1, "商品促销不能为空");
            }
            if (StringUtils.isEmpty(product.getUnit())) {
                return R.error(-1, "商品单位不能为空");
            }
            if (StringUtils.isEmpty(product.getStock())) {
                return R.error(-1, "商品库存数不能为空");
            }
            if (StringUtils.isEmpty(product.getDescription())) {
                return R.error(-1, "商品描述不能为空");
            }
            if (StringUtils.isEmpty(product.getDetail())) {
                return R.error(-1, "商品详情不能为空");
            }
            if (Objects.isNull(product.getPrice())) {
                return R.error(-1, "商品价格不能为空");
            }
            String addProduct = productService.saveProduct(product);
            return R.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(-1, "添加失败");
        }

    }

    /**
     * 获取商品信息
     */
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R List(@RequestBody Map<String, Object> result, @LoginUser Manager manager) {
        if (manager == null){
            return R.error(-1, "未登录");
        }
        try {
            if (StringUtils.isEmpty(result.get("page")) || StringUtils.isEmpty(result.get("limit"))) {
                return R.error(-1, "请求错误");
            }
            PageQuery pageQuery = new PageQuery(result);
            return R.ok().put("data", productService.getProductInfo(pageQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(-405, "查询列表失败，msg:" + e.getMessage());
        }
    }

    /**
     * 根据id获取商品信息
     */
    @GetMapping(value = "/get/{id}")
//    @ResponseBody
    public String getProductById(HttpServletRequest request,@PathVariable("id") Integer id, @LoginUser Manager manager){
        if (manager == null){
            return "未登录";
        }
        Product info = productService.getProductById(id);
        if (info != null){
            request.setAttribute("goods",info);
            request.setAttribute("path","goods-edit");
            return "goods_edit";
        }
        return null;
    }

    @RequestMapping(value = "/get", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R getProductInfo(HttpServletRequest request,@RequestParam("id") Integer id, @LoginUser Manager manager){
        if (manager == null){
            return R.error(-1, "未登录");
        }
        Product info = productService.getProductById(id);
        if (info != null){
            request.setAttribute("goods",info);
            request.setAttribute("path","goods-edit");
            return R.ok().put("data",info);
        }
        return R.error("查询错误");
    }
    /**
     * 修改商品信息
     */
    @RequestMapping(value = "/update", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R updateProduct(@RequestBody Product product, @LoginUser Manager manager) {
        if (manager == null){
            return R.error(-1, "未登录");
        }
        if (Objects.isNull(product.getId())) {
            return R.error(-1, "请选择一条记录");
        }
        String result = productService.updateProductInfo(product);
        return R.ok("修改成功");
//        return R.ok().put("result", result);
    }

    /**
     * 删除商品信息
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R delete(@RequestBody Integer[] ids, @LoginUser Manager manager) {
        if (manager == null){
            return R.error(-1, "未登录");
        }
        if (ids.length < 1) {
            return R.error(-1, "参数异常");
        }
        if (productService.deleteProduct(ids)) {
            return R.ok("删除成功");
        } else {
            return R.error(-1, "删除失败");
        }
    }

    /**
     * 修改审核状态
     */
    @RequestMapping(value = "/update/VerifyStatus", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R updateVerifyStstus(@RequestBody Integer[] ids, @RequestParam("verifyStatus") int verifyStatus, @LoginUser Manager manager) {
        if (manager == null){
            return R.error(-1, "未登录");
        }
        if (ids.length < 1) {
            return R.error(-1, "参数异常");
        }
        int count = productService.updateVerifyStatus(ids, verifyStatus);
        if (count > 0) {
            return R.ok().put("count", count);
        } else {
            return R.error(-1, "修改异常");
        }
    }

    /**
     * 修改上下架状态
     */
    @RequestMapping(value = "/update/publicStatus/{publicStatus}", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R updatePublicStatus(@RequestBody Integer[] ids, @PathVariable("publicStatus") int publicStatus, @LoginUser Manager manager) {
        if (manager == null){
            return R.error(-1, "未登录");
        }
        if (ids.length < 1) {
            return R.error(-1, "参数异常");
        }
        if (publicStatus != Contants.SELL_STATUS_UP && publicStatus != Contants.SELL_STATUS_DOWN) {
            return R.error(-1, "参数异常");
        }
        int count = productService.updatePublicStatus(ids, publicStatus);
        if (count > 0) {
            return R.ok("修改成功");
        } else {
            return R.error(-1, "修改异常");
        }
    }

    /**
     * 修改推荐状态
     */
    @RequestMapping(value = "/update/recomandStatus/{recomandStatus}", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R updateRecomandStatus(@RequestBody Integer[] ids, @PathVariable("recomandStatus") int recomandStatus, @LoginUser Manager manager) {
        if (manager == null){
            return R.error(-1, "未登录");
        }
        if (ids.length < 1) {
            return R.error(-1, "参数异常");
        }
        int count = productService.updateRecomandStatus(ids, recomandStatus);
        if (count > 0) {
            return R.ok().put("num", count);
        } else {
            return R.error(-1, "修改异常");
        }
    }

    /**
     * 修改库存数
     */
    @RequestMapping(value = "/update/stock", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R updateStock(@RequestBody Integer[] ids,Product goods) {
        Integer result = productService.updateStock(ids,goods);
        // 获取库存数
        // goods.setStock(productService.selectIdList());
        return R.ok().put("success",result);
    }
    /**
     * 修改销售量
     * @param id
     */
    @RequestMapping(value = "/update/sale", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R updateSale(@RequestBody Integer[] id) {
        Integer result = productService.updateSaleNumber(id);
        return R.ok().put("success",result);
    }

    /**
     * 根据商品名称或者货号模糊查询
     */
    @RequestMapping(value = "/update/search", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R searchProduct(@RequestParam Map<String, Object> params, HttpServletRequest request, @LoginUser Manager manager){
        if (manager == null){
            return R.error(-1, "未登录");
        }
        if (StringUtils.isEmpty(params.get("page"))){
            params.put("page",1);
        }
        String productName = "";
        String description = "";
        String productSn = "";
        //对keyword做去空格处理
        if (params.containsKey("productName") && !StringUtils.isEmpty((params.get("productName") + "").trim())) {
            productName = params.get("productName") + "";
        }
        request.setAttribute("productName", productName);
        params.put("productName",productName);

        if (params.containsKey("description") && !StringUtils.isEmpty((params.get("description") + "").trim())) {
            description = params.get("description") + "";
        }
        request.setAttribute("description", description);
        params.put("description",description);

        if (params.containsKey("productSn") && !StringUtils.isEmpty((params.get("productSn") + "").trim())) {
            productSn = params.get("productSn") + "";
        }
        request.setAttribute("productSn", productSn);
        params.put("productSn",productSn);
        //封装商品数据
        PageQuery pageQuery = new PageQuery(params);
        return R.ok().put("data",productService.searchProduct(pageQuery));
    }
    /**
     * 获取商品详情
     * @PathVariable 占位符，截取URL的一部分
     */
    @RequestMapping(value = "/product/detail/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R productDetail(@PathVariable("id") Integer id){
        Product product = productService.selectProductById(id);
        if (product == null){
            return R.error(-1,"参数错误，查询为空");
        }
        return R.ok().put("data",product);
    }
}