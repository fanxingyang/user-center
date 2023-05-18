package com.xiaofan.usercenterbackend1.mapper.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author xFAN
 */
//在传递参数的时候可以继承序列化接口

    //生成get和set方法
    @Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 2317341388814033834L;

    private  String userAccount;

    private  String userPassword;

    private  String checkPassword;



}
