package com.eproject.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单修改收货人信息参数
 * 9.
 */
@Getter
@Setter
public class ReceiverInfoParam {
    private Integer orderId;
    private Integer userId;
    private String name;
    private String phoneNumber;
    private String postCode;
    private String detailAddress;
    private String province;
    private String city;
    private String region;
    private Integer status;

}
