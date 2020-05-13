package com.eproject.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    public static String APP_ID = "2016102400750993";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "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";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn+fdblsW8UHexEMvRnwIO6Wvz4LRPWrMQ2f/r40eiGqEdlVv4tPcK1v+JKJHTWsX84pE5t/aPRUt4ELP6AA7XJhciPBbyZ2hVfc/r+RmERuAmnikC2MS1G8N0s8yE+dCfmajb9lcPExajU8w/XWVtJR6a3GLE3uBs+5twzB1+ykkZnQ3hedW9ROdkHh1WrpY1xcOGPsWqv9gy98qyt7zBECzJTojonMQIxw12TOdjj8ZsOzvt/rVO051yuCKAvGsqT6BlyxQ3yzSKdSqiC9KroknCE5ppDOlY3Me5ix4JBp6i1+2R+UX3w3QUZtF6MF5tNNrkEgneY9nRyxWFUyCwQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/html/mall/my_orders.html";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    public static String return_url = "http://localhost:8080/html/mall/my_orders.html";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关，这是沙箱的网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

