package com.xiaofan.usercenterbackend1.service;

import com.xiaofan.usercenterbackend1.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
* @author 小fan
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-05-16 21:17:49
*/
public interface UserService extends IService<User> {


    /**
     * 用户注册
     *
     * @param userAccount 用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     *
     * @return 新用户id
     */
    long userRegister(String userAccount,String userPassword, String checkPassword );

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request 设置用户登录信息
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);
}
