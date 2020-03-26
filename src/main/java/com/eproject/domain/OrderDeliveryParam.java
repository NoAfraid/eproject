package com.eproject.domain;

import com.eproject.util.NumberUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单发货参数
 */
@Getter
@Setter
public class OrderDeliveryParam {
    @ApiModelProperty("订单id")
    private Integer orderId;
    @ApiModelProperty("物流公司")
    private String deliveryCompany;
    @ApiModelProperty("物流单号")
    private String  deliveryNo = NumberUtil.genDeliveryNo();

}

