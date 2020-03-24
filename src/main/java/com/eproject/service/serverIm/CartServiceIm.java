package com.eproject.service.serverIm;

import com.eproject.dao.CartDao;
import com.eproject.dao.UserDao;
import com.eproject.entity.Cart;
import com.eproject.entity.User;
import com.eproject.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceIm implements CartService {
    @Resource
    private CartDao cartDao;

    @Resource
    private UserDao userDao;

    @Override
    public int add(Cart cart){
        int count = 0;
        // 获取用户信息（用户ID和用户Nick）
        //User userInfo = new User();
        //User selectInfo = userDao.selectUser(userInfo.getId());
        cart.setUserId(cart.getUserId());
        //Cart existCart = get
        // 获取购物车商品
        Cart product = cartDao.selectProductExist(cart.getUserId(),cart.getProductId());
        //判断是否存在该商品（为null则插入改商品，否则增加商品数量）
        if (product == null){
            cart.setCreateTime(new Date());
            count = cartDao.insert(cart);
        }else {
            product.setUpdataTime(new Date());
            //统计商品数量
            product.setProductCount(product.getProductCount() + cart.getProductCount());
            cartDao.updateByPrimaryKey(product);
            count = product.getProductCount();
            //是否要设置购买数量最大为5
            //if (count > 5){
             //   return -2;
            //}
        }
        return count;
    }

    @Override
    public int updateQuantity(Cart cart){//Integer id, Integer userId, Integer productCount
        //Cart cart = new Cart();
        cart.setProductCount(cart.getProductCount());
        cart.setProductId(cart.getProductId());
        cart.setUserId(cart.getUserId());
        cart.setId(cart.getId());
        cart.setUpdataTime(new Date());
        return cartDao.updateQuantity(cart);
    }

    @Override
    public List<Cart> selectCartInfo(Integer  userId){
        List<Cart> list = cartDao.selectCartInfo(userId);
        return list;
    }

    @Override
    public int deleteCart(Cart cart, List<Integer> ids){//Integer[] ids
        //deleteStatus(1删除，0保留)
        cart.setDeleteStatus(1);
        int num = cartDao.updateDeleteStatus(cart.getUserId(),cart.getProductId(),ids);
        return num;
    }

    @Override
    public int clear(Integer userId){
        int num = cartDao.clearCart(userId);
        return num;
    }
}
