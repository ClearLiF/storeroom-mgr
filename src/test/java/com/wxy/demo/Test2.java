package com.wxy.demo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import org.junit.Test;

import java.util.Date;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : Test2
 * @packageName : com.wxy.demo
 * @description : 一般类
 * @date : 2021-03-26 15:02
 **/
public class Test2 {

    @Test
    public void test1(){
        DateTime beginOfYear = DateUtil.beginOfYear(new Date());
        DateTime dateTime = DateUtil.endOfMonth(beginOfYear);
        System.out.println(beginOfYear.getTime());
       // System.out.println(System.currentTimeMillis());
    }
    @Test
    public void test2(){
        //System.out.println(test3());
    }

    public void  test3(){
        //return  1+1;

    }
}
