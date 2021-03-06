package com.eproject.controller;

import com.eproject.common.Contants;
import com.eproject.common.R;
import com.eproject.entity.Cart;
import com.eproject.entity.User;
import com.eproject.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/cart",produces = "application/json;charset=UTF-8")
public class CartController {

    @Resource
    private CartService cartService;

    /**
     * 查询购物车中是否包含该商品，有增加数量，无添加到购物车
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/insert",produces = "application/json;charset=UTF-8")
    public R insertCart(@RequestBody Cart cart, HttpSession httpSession){
        //获取用户session
//        User user = (User) httpSession.getAttribute(httpSession.getId());
        //正式用
//        cart.setUserId(user.getId());
        //测试的用
        cart.setUserId(cart.getUserId());
        cart.setProductId(cart.getId());
        cart.setProductCount(cart.getProductCount());
        //判断数量
        int num = cartService.add(cart);
        if (num > 0){
            return R.ok().put("data","添加成功");
        }
        //if (num == -2){
        //    return R.error(-2,"超过最大购买数量");
        //}
        return R.error(-1,"添加出错");
    }
    /**
     * 修改某个购物车商品的数量
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/updateQuantity",produces = "application/json;charset=UTF-8")
    public R updateQuantity(@RequestBody Cart cart){
        //@RequestParam("id") Integer id,
        //                            @RequestParam("userId") Integer userId,
        //                            @RequestParam("productId") Integer productId,
        int count = cartService.updateQuantity(cart);
        if (count > 0){
            return R.ok().put("count",count);
        }
        return R.error(-1,"修改错误");
    }

    /**
     * 获取我的购物车中的列表数据
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/selectCartInfo",produces = "application/json;charset=UTF-8")
    public R selectCartInfo(@RequestBody Cart cart, HttpServletRequest request){
        int itemsTotal = 0;
        BigDecimal totalAmount = new BigDecimal("0");
        List<Cart> cartList = cartService.selectCartInfo(cart.getUserId());
        if (!CollectionUtils.isEmpty(cartList)){
            itemsTotal = cartList.stream().mapToInt(Cart::getProductCount).sum();
            for (Cart cartNum : cartList){
                totalAmount = totalAmount.add(cartNum.getPrice().multiply(new BigDecimal(cartNum.getProductCount())));
            }
            request.setAttribute("itemsTotal",itemsTotal);
            request.setAttribute("totalAmount",totalAmount);
            return R.ok().put("data",cartList);
        }
        return R.error(-1,"购物车为空");
    }

    /**
     * 获取我的购物车中的列表数据
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/selectCartList",produces = "application/json;charset=UTF-8")
    public R selectCartList(@RequestBody Cart cart, HttpServletRequest request){
        int itemsTotal = 0;
        BigDecimal totalAmount = new BigDecimal("0");
        List<Cart> cartList = cartService.selectCartInfo(cart.getUserId());
        if (!CollectionUtils.isEmpty(cartList)){
            itemsTotal = cartList.stream().mapToInt(Cart::getProductCount).sum();
            for (Cart cartNum : cartList){
                totalAmount = totalAmount.add(cartNum.getPrice().multiply(new BigDecimal(cartNum.getProductCount())));
            }
            Map<String, Object> map=new HashMap<String,Object>();
            map.put("itemsTotal",itemsTotal);
            map.put("totalAmount",totalAmount);
            return R.ok().put("data",map);
        }
        return R.error(-1,"购物车为空");
    }
    /**
     * 跳转到下单页
     */
//    @RequestMapping(method= RequestMethod.POST, value = "/selectCartList",produces = "application/json;charset=UTF-8")
//    public R selectCartList(@RequestBody Cart cart, HttpServletRequest request){
//        int itemsTotal = 0;
//        BigDecimal totalAmount = new BigDecimal("0");
//        List<Cart> cartList = cartService.selectCartInfo(cart.getUserId());
//        if (!CollectionUtils.isEmpty(cartList)){
//            itemsTotal = cartList.stream().mapToInt(Cart::getProductCount).sum();
//            for (Cart cartNum : cartList){
//                totalAmount = totalAmount.add(cartNum.getPrice().multiply(new BigDecimal(cartNum.getProductCount())));
//            }
//            Map<String, Object> map=new HashMap<String,Object>();
//            map.put("itemsTotal",itemsTotal);
//            map.put("totalAmount",totalAmount);
//            return R.ok().put("data",map);
//        }
//        return R.error(-1,"查询为空");
//    }
    /**
     * 批量删除购物车中的商品
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/deleteCartInfo",produces = "application/json;charset=UTF-8")
    public R deleteCartInf(@RequestBody Cart cart,@RequestParam("ids") List<Integer> ids){//Integer[] ids
        //,@RequestParam("id") List<Integer> id
        int count = cartService.deleteCart(cart,ids);
        if (count > 0){
            return R.ok().put("data",count);
        }
        return R.error(-1,"删除出错");
    }
    /**
     * 清空购物车
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/clearCartInfo",produces = "application/json;charset=UTF-8")
    public R clearCartInfo(@RequestBody Cart cart){
        int count = cartService.clear(cart.getUserId());
        if (count > 0){
            return R.ok().put("购物车已清空",count);
        }
        return R.error(-1,"删除出错");
    }
    /**
     * 统计个人购物车中的商品数量信息
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/count",produces = "application/json;charset=UTF-8")
    public R count(@RequestBody Cart cart){
        int count = cartService.count(cart.getUserId());
        if (count > 0){
            return R.ok().put("data",count);
        }
        return R.error(-1,"选择出错");
    }
    /**
     * 获取购物车中用于选择商品规格的商品信息
     */

}
