package com.eproject.service.serverIm;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.eproject.common.OrderStatusEnum;
import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.common.Result;
import com.eproject.config.AlipayConfig;
import com.eproject.dao.*;
import com.eproject.domain.ConfirmOrderResult;
import com.eproject.domain.OrderDetail;
import com.eproject.domain.ReceiverInfoParam;
import com.eproject.entity.*;
import com.eproject.service.OrderService;
import com.eproject.service.UserReceiveAddressService;
import com.eproject.util.NumberUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private ShuStockDao skuStockMapper;

    @Resource
    private PortalOrderDao portalOrderDao;

    @Resource
    private ProductDao productDao;

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
            orderItem.setOrderStatus(0);
//            orderItem.setProductAttr(cartList.getProductAttr());
            orderItemList.add(orderItem);
        }
        //判断购物车中商品是否都有库存
        if (!hasStock(list)){
            return null;
        }
        //计算order_item的实付金额
        //数据库减去已买的商品数量

        //进行库存锁定
        lockStock(list);
        //根据商品合计、运费计算应付金额

        Order orderList = new Order();
        for (Cart cartList : list){
            orderList.setTotalPrice(calcTotalAmount(orderItemList));
            orderList.setFreightAmount(new BigDecimal(0));
            //转化为订单信息并插入数据库
            orderList.setProductName(cartList.getProductName());
            orderList.setProductId(cartList.getProductId());
            orderList.setUserId(cartList.getUserId());
            orderList.setCreatTime(new Date());
//        orderList.setUserId(order.getUserId());
            //支付方式：0->未支付；1->支付宝；2->微信
            orderList.setPayType(order.getPayType());
        }

        //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        orderList.setOrderStatus(order.getOrderStatus());
        //收货人信息：姓名、电话、邮编、地址
        UserReceiveAddress address = userReceiveAddressService.getItem(order.getUserId());
        orderList.setReceiverName(address.getName());
        orderList.setReceiverPhone(address.getPhoneNumber());
//        orderList.setReceiverPostCode(address.getPostCode());
        orderList.setReceiverProvince(address.getProvince());
        orderList.setReceiverDetailAdress(address.getDetailAddress());
        orderList.setReceiverCity(address.getCity());
//        orderList.setRegion(address.getCity());
        //orderList.setPayTime(new Date());
        //0->未确认；1->已确认
        orderList.setDaleteStatus(0);
        //0->未支付；1->已支付
        orderList.setOrderStatus(0);
        //生成订单号
        orderList.setOrderNo(NumberUtil.genOrderNo());
        //插入order表和order_item表
        orderDao.insert(orderList);
        for (OrderItem orderItem : orderItemList){
            //OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderList.getId());
            orderItem.setOrderNo(orderList.getOrderNo());
            //orderItem.setOrderStatus(0);
            orderItem.setCreateTime(new Date());
            //orderItem.setProductName(orderList.getProductName());
            //orderItem.setProductId(orderList.getProductId());
            //orderItem.setProductImg(orderItem.getProductImg());
            //orderItem.setProductPrice(orderList.getTotalPrice());
            //orderItem.setUserId(orderList.getUserId());
        }
        orderItemDao.insertList(orderItemList);
        //删除购物车中的下单商品
        User user = new User();
        List<Integer> productList = new ArrayList<>();
        deleteCartItemList(list,order.getUserId(),productList);
        //发送延迟消息取消订单
        sendDelayMessageCancelOrder(order.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("order", orderList);
        result.put("orderItemList", orderItemList);
        return result;
    }

    @Override
    public PageResult getMyOrders(PageQuery pageQuery){
        List<OrderItem> orderList = orderItemDao.selectByUserId(pageQuery);
        int total = orderDao.getPage(pageQuery);
        PageResult pageResult = new PageResult(orderList, total, pageQuery.getLimit(), pageQuery.getPage());
        return pageResult;
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

    @Override
    public Order getOrderById(Integer id){
        Order temp = orderDao.selectByPrimaryKey(id);
        if (temp == null){
            return null;
        }
        return temp;
    }

    @Override
    public  int updateReceiverInfo(ReceiverInfoParam infoParam){
        //前端修改
        Order order = new Order();
        order.setId(infoParam.getOrderId());
        order.setReceiverName(infoParam.getName());
        order.setReceiverPhone(infoParam.getPhoneNumber());
        order.setReceiverPostCode(infoParam.getPostCode());
        order.setReceiverProvince(infoParam.getProvince());
        order.setReceiverDetailAdress(infoParam.getDetailAddress());
        order.setReceiverCity(infoParam.getCity());
        int count = orderDao.updateByPrimaryKey(order);
        return count;
    }

    @Override
    public Integer cancelTimeOutOrder(){
        Integer count=0;
        OrderSetting orderSetting = orderSettingDao.selectByPrimaryKey(1L);
        //查询超时、未支付的订单及订单详情
        List<OrderDetail> timeOutOrders =orderDao.getTimeOutOrders(orderSetting.getNormalOrderOvertime());
        if (CollectionUtils.isEmpty(timeOutOrders)) {
            return count;
        }
        //修改订单状态为交易取消
        List<Integer> ids = new ArrayList<>();
        for (OrderDetail timeOutOrder : timeOutOrders){
            ids.add(timeOutOrder.getId());
        }
        orderDao.updateOrderStatus(ids,4);
        for (OrderDetail timeOutOrder : timeOutOrders){
            //解除订单商品库存锁定,释放商品数量
        }
        return timeOutOrders.size();
    }
    /**
     * 获取用户订单购物信息
     */
    @Override
    public List<Order> getOrderByOrderNo(String orderNo){
        List<Order> orderList = orderDao.selectByOrderNoList(orderNo);
        if (orderList.size() > 0 ){
            return orderList;
        }
        return null;
    }
    @Override
    public List<OrderItem> getByOrderNo(String orderNo){
        List<OrderItem> orderItemList = orderItemDao.selectByOrderNoList(orderNo);
        if (orderItemList.size() > 0 ){
            return orderItemList;
        }
        return null;
    }
    /**
     *
     * @param orderNo
     * @param userId
     * @return
     */

    @Override
    public String cancelOrder(String orderNo, Integer userId){
        Order order = orderDao.selectByOrderNo(orderNo);
        List<OrderItem> orderItem = orderItemDao.selectByOrderNoList(orderNo);
//        if (orderItem.size() > 0 ){
//
//        }
        if (order != null){
            //todo 验证是否是当前userId下的订单，否则报错
            //todo 订单状态判断
            if (orderDao.updateOrderStatus(Collections.singletonList(order.getId()),
                    OrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) >0){
                //释放锁定的库存
                portalOrderDao.releaseSkuStockLock(orderItem);
                return Result.SUCCESS.getResult();
            }
            return Result.DB_ERROR.getResult();
        }
        return Result.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public Order pay(String orderNo){
        return orderDao.selectByOrderNo(orderNo);
    }
    @Override
    public Integer paySuccess(String orderNo, String payType){
        //修改订单支付状态
        Order o = orderDao.selectByOrderNo(orderNo);
        OrderItem OI = new OrderItem();
        List<OrderItem> orderItem = orderItemDao.selectByOrderNoList(orderNo);
        for (OrderItem item: orderItem){
            OI.setId(item.getId());
            OI.setOrderStatus(1);
            orderItemDao.updateByPrimaryKeySelective(OI);
        }
        Order order = new Order();
        order.setId(o.getId());
        order.setOrderStatus(1);
        //order.setOrderNo(orderNo);
        order.setPayStatus(1);
        order.setPayType(payType);
        order.setPayTime(new Date());

        orderDao.updateByPrimaryKeySelective(order);
        //恢复所有下单商品的锁定库存，扣减真实库存
        OrderDetail orderDetail = portalOrderDao.getDetail(o.getId());
        List<OrderItem> od = orderDetail.getOrderItemList();
        //
        //productDao.updateSkuStock(od);
        int count = portalOrderDao.updateSkuStock(od);
        //将shu_stock 中的stock和sale赋值到product表中
        productDao.updateStockAndSale(od);
        return count;
    }

    @Override
    public  String finishOrder(String orderNo, Integer userId){
        Order order = orderDao.selectByOrderNo(orderNo);
        OrderItem OI = new OrderItem();
        List<OrderItem> orderItem = orderItemDao.selectByOrderNoList(orderNo);
        for (OrderItem item: orderItem){
            OI.setId(item.getId());
            OI.setOrderStatus(4);
            orderItemDao.updateByPrimaryKeySelective(OI);
        }
        if (order != null){
            //todo 验证是否是当前userId下的订单，否则报错
            //todo 订单状态判断
           order.setOrderStatus((int) OrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
           order.setOrderStatus(4);
           order.setUpdataTime(new Date());
           if (orderDao.updateByPrimaryKeySelective(order) > 0){
               return Result.SUCCESS.getResult();
           }
            return Result.DB_ERROR.getResult();
        }
        return Result.ORDER_NOT_EXIST_ERROR.getResult();
    }












    /**
     * 锁定下单商品的所有库存
     */
    private void lockStock(List<Cart> cartPromotionItemList) {
        for (Cart cartPromotionItem : cartPromotionItemList) {
            ShuStock skuStock = skuStockMapper.selectByPrimaryKey(cartPromotionItem.getProductId());
            skuStock.setLockStock(skuStock.getLockStock() + cartPromotionItem.getProductCount());
            skuStockMapper.updateByPrimaryKeySelective(skuStock);
        }
    }


    /**
     * 删除下单商品的购物车信息
     */
    private void deleteCartItemList(List<Cart> cartList, Integer userId,List<Integer> productList) {
        List<Integer> ids = new ArrayList<>();
        //List<Integer> productList = new ArrayList<>();
        for (Cart cart : cartList){
            ids.add(cart.getId());
            productList.add(cart.getProductId());
        }
        cartDao.DeleteStatus(userId,productList,ids);
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
