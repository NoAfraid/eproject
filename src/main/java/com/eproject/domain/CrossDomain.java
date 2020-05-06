package com.eproject.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;

public class CrossDomain {
    // 初始化 CorsConfiguration 对象并设置允许的域名、请求头部信息和请求方式
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 2 允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3 允许任何方法（post、get 等）
        return corsConfiguration;
    }
//    /**
//     * 创建 CorsFilter 对象
//     * @return CorsFilter
//     */
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig()); //拦截所有请求
//        return new CorsFilter(source);
//    }
}
