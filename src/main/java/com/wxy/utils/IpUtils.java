package com.wxy.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : wxy
 * @version : V1.0
 * @className : IpUtils
 * @packageName : com.yinghuo.framework.utils
 * @description : 一般类
 * @date : 2021-03-02 16:34
 **/
public class IpUtils {

    public static String  getIpByRequest(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }
        return ip;
    }
}
