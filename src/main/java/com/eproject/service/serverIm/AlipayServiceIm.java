package com.eproject.service.serverIm;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.eproject.config.AlipayConfig;
import com.eproject.dao.AlipayDao;
import com.eproject.entity.Alipay;
import com.eproject.service.AlipayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class AlipayServiceIm implements AlipayService {

    @Resource
    private AlipayDao alipayDao;

    /** 调取支付宝接口 web端支付*/
    DefaultAlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);

    @Override
    public void webPagePay(Alipay alipay) throws Exception{
        alipayDao.insertSelective(alipay);
    }
    @Override
    public String refund(String orderNo, String refundReason, BigDecimal refundAmount, String outRequestNo) throws AlipayApiException {
        Alipay alipay  = alipayDao.selectByOrderNo(orderNo);
        if (alipay != null){
            AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
            /** 调取接口*/
            alipayRequest.setBizContent("{\"out_trade_no\":\""+ orderNo +"\","
                    + "\"refund_amount\":\""+ refundAmount +"\","
                    + "\"refund_reason\":\""+ refundReason +"\","
                    + "\"out_request_no\":\""+ outRequestNo +"\"}");
            String result = alipayClient.execute(alipayRequest).getBody();
            alipay.setId(alipay.getId());
            alipay.setRefundReason(refundReason);
            alipay.setRefundAmount(refundAmount);
            alipay.setOutrequestno(outRequestNo);
            BigDecimal AccountAmount = alipay.getAccountAmount().add(refundAmount);
            alipay.setAccountAmount(AccountAmount);
            alipay.setRefundTime(new Date());
            alipay.setOrderStatus(-5);
            //alipayDao.insert(alipay);
            alipayDao.updateByPrimaryKey(alipay);
            return result;
        }
        return null;
    }

    @Override
    public String refundQuery(String outTradeNo , String outRequestNo) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

        /** 请求接口*/
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\","
                +"\"out_request_no\":\""+ outRequestNo +"\"}");

        /** 格式转换*/
        String result = alipayClient.execute(alipayRequest).getBody();

        return result;
    }
}
