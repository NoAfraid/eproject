package com.eproject.service.serverIm;

import com.eproject.dao.CartDao;
import com.eproject.dao.OrderDao;
import com.eproject.dao.UserReceiveAddressDao;
import com.eproject.domain.ConfirmOrderResult;
import com.eproject.entity.Cart;
import com.eproject.entity.Order;
import com.eproject.entity.OrderItem;
import com.eproject.entity.UserReceiveAddress;
import com.eproject.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceIm implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private CartDao cartDao;

    @Resource
    private UserReceiveAddressDao userReceiveAddressDao;

    @Override
    public ConfirmOrderResult generateConfirmOrder(){
        ConfirmOrderResult result = new ConfirmOrderResult();
        //根据用户Id获取购物车信息
        Cart cart = new Cart();
        List<Cart> list = cartDao.selectCartInfo(cart.getUserId());
        result.setCartPromotionItemList(list);
        //获取用户收货地址列表
        List<UserReceiveAddress> userReceiveAddressList =  userReceiveAddressDao.selectByPrimaryKey(cart.getUserId());
        result.setMemberReceiveAddressList(userReceiveAddressList);
        // 计算总金额
        ConfirmOrderResult.CalcAmount calcAmount = calcCartAmount(list);
        result.setCalcAmount(calcAmount);
        return result;
    }

    @Override
    public String generateOrder(Order order){
        List<OrderItem> orderItemList = new ArrayList<>();
        //获取购物车信息
        List<Cart> list = cartDao.selectCartInfo(order.getUserId());
        OrderItem orderItem = new OrderItem();
        for (Cart cartList : list){
            //生成订单信息
            orderItem.setProductId(cartList.getProductId());
            orderItem.setProductName(cartList.getProductName());
            orderItem.setProductImg(cartList.getProductImg());
            orderItem.setProductPrice(cartList.getPrice());
            orderItem.setProductQuantity(cartList.getProductCount());
            orderItem.setUserId(cartList.getUserId());
            orderItem.setProductAttr(cartList.getProductAttr());
            orderItemList.add(orderItem);
        }
        //判断购物车中商品是否都有库存
        if (!hasStock(list)){
            return "库存不足，无法下单";
        }
        //计算order_item的实付金额

        //数据库减去已买的商品数量

        //根据商品合计、运费计算应付金额
        order.setTotalPrice(calcTotalAmount(orderItemList));
        order.setFreightAmount(new BigDecimal(0));
        //转化为订单信息并插入数据库
        order.setUserId(order.getUserId());
        orderItem.setUserId(order.getUserId());
        orderItem.setCreateTime(new Date());
        //支付方式：0->未支付；1->支付宝；2->微信
        order.setPayType(order.getPayType());
        //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        order.setOrderStatus(order.getOrderStatus());
        return "";
    }


    /**
     * 计算购物车中的商品价格
     */
    private ConfirmOrderResult.CalcAmount calcCartAmount(List<Cart> list){
        ConfirmOrderResult.CalcAmount calcAmount = new ConfirmOrderResult.CalcAmount();
        calcAmount.setFreightAmount(new BigDecimal(0));
        BigDecimal totalAmount = new BigDecimal("0");
        for (Cart cartList : list){
            totalAmount = totalAmount.add(cartList.getPrice().multiply(new BigDecimal(cartList.getProductCount())));
        }
        calcAmount.setTotalAmount(totalAmount);
        calcAmount.setPayAmount(totalAmount);
        return calcAmount;
    }

    /**
     * 判断下单商品是否都有库存
     */
    private boolean hasStock(List<Cart> cartList){
        for (Cart list : cartList){
            if (list.getProductCount()== null||list.getProductCount()<0){
                return false;
            }
        }
        return true;
    }

    /**
     * 计算总金额
     */
    private BigDecimal calcTotalAmount(List<OrderItem> orderItemList) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OrderItem item : orderItemList) {
            totalAmount = totalAmount.add(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        return totalAmount;
    }

    /**
     * 锁定下单商品的所有库存

    private void lockStock(List<Cart> cartList){
        for (Cart list : cartList){

        }
    }
     */
}
