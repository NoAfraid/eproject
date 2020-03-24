package com.eproject.service.serverIm;

import com.eproject.common.PageQuery;
import com.eproject.common.Result;
import com.eproject.dao.*;
import com.eproject.domain.ConfirmOrderResult;
import com.eproject.domain.OrderDetail;
import com.eproject.entity.*;
import com.eproject.service.OrderService;
import com.eproject.service.UserReceiveAddressService;
import com.eproject.util.NumberUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceIm implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private CartDao cartDao;

    @Resource
    private UserReceiveAddressDao userReceiveAddressDao;

    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private OrderSettingDao orderSettingDao;

    @Resource
    private UserReceiveAddressService userReceiveAddressService;

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
    public Map<String, Object> generateOrder(Order order){
        List<OrderItem> orderItemList = new ArrayList<>();
        //获取购物车信息
        List<Cart> list = cartDao.selectCartInfo(order.getUserId());
        for (Cart cartList : list){
            //生成订单信息
            OrderItem orderItem = new OrderItem();
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
            return null;
        }
        //计算order_item的实付金额

        //数据库减去已买的商品数量

        //根据商品合计、运费计算应付金额
        Order orderList = new Order();
        orderList.setTotalPrice(calcTotalAmount(orderItemList));
        orderList.setFreightAmount(new BigDecimal(0));
        //转化为订单信息并插入数据库
        orderList.setUserId(order.getUserId());
        orderList.setUserId(order.getUserId());
        //支付方式：0->未支付；1->支付宝；2->微信
        orderList.setPayType(order.getPayType());
        //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        orderList.setOrderStatus(order.getOrderStatus());
        //收货人信息：姓名、电话、邮编、地址
        UserReceiveAddress address = userReceiveAddressService.getItem(order.getUserId());
        orderList.setReceiverName(address.getName());
        orderList.setReceiverPhone(address.getPhoneNumber());
        orderList.setReceiverPostCode(address.getPostCode());
        orderList.setReceiverProvince(address.getProvince());
        orderList.setReceiverDetailAdress(address.getDetailAddress());
        orderList.setReceiverCity(address.getCity());
        orderList.setPayTime(new Date());
        //0->未确认；1->已确认
        orderList.setDaleteStatus(0);
        //生成订单号
        orderList.setOrderNo(NumberUtil.genOrderNo());
        //插入order表和order_item表
        orderDao.insert(orderList);
        for (OrderItem orderItem : orderItemList){
            //OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderList.getId());
            orderItem.setOrderNo(orderList.getOrderNo());
            orderItem.setCreateTime(new Date());
            orderItem.setProductName(orderList.getProductName());
            orderItem.setProductPrice(orderList.getTotalPrice());
            orderItem.setUserId(orderList.getUserId());
        }
        orderItemDao.insertList(orderItemList);
        //删除购物车中的下单商品
        User user = new User();
        Product product = new Product();
        deleteCartItemList(list,user,product);
        //发送延迟消息取消订单
        sendDelayMessageCancelOrder(order.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("order", orderList);
        result.put("orderItemList", orderItemList);
        return result;
    }

    @Override
    public List<Order> getMyOrders(PageQuery pageQuery){
        return null;
    }

    @Override
    public int sendDelayMessageCancelOrder(Integer orderId) {
        //获取订单超时时间
        OrderSetting orderSetting = orderSettingDao.selectByPrimaryKey(1L);
        Integer delayTimes = orderSetting.getNormalOrderOvertime() * 60 * 1000;
//        //发送延迟消息
//        cancelOrderSender.sendMessage(orderId, delayTimes);
        return delayTimes;
    }
    /**
     * 删除下单商品的购物车信息
     */
    private void deleteCartItemList(List<Cart> cartList, User user,Product product) {
        List<Integer> ids = new ArrayList<>();
        for (Cart cart : cartList){
            ids.add(cart.getId());
        }
        cartDao.updateDeleteStatus(user.getId(),product.getId(),ids);
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
