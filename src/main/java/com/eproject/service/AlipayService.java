package com.eproject.service;

import com.alipay.api.AlipayApiException;
import com.eproject.entity.Alipay;

import java.math.BigDecimal;

public interface AlipayService {

    void webPagePay(Alipay alipay) throws Exception;

    /**
     * 退款
     * @param outTradeNo    订单编号
     * @param refundReason  退款原因
     * @param refundAmount  退款金额
     * @param outRequestNo  标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
     */
    String refund(String outTradeNo, String refundReason, BigDecimal refundAmount, String outRequestNo) throws AlipayApiException;

    /**
     * 退款查询
     * @param outTradeNo 订单编号（唯一）
     * @param outRequestNo 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
     */
    String refundQuery(String outTradeNo,String outRequestNo) throws AlipayApiException;
}
