package com.xiaofan.usercenterbackend1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaofan.usercenterbackend1.mapper.request.UserLoginRequest;
import com.xiaofan.usercenterbackend1.mapper.request.UserRegisterRequest;
import com.xiaofan.usercenterbackend1.model.domain.User;
import com.xiaofan.usercenterbackend1.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.xiaofan.usercenterbackend1.constant.UserConstant.ADMIN_ROLE;
import static com.xiaofan.usercenterbackend1.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author xFan
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null){
            return  null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);

    }

    @PostMapping("/login")
    public User userRegister(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest == null){
            return  null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

         if (StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        return userService.userLogin(userAccount,userPassword,request);

    }

    @GetMapping("/search")
    public List<User> searchUsers(String username,HttpServletRequest request){
        //鉴权 仅管理员可查询 获取登录态
//        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User user = (User) userObject;
//        if (user == null || user.getRole() != ADMIN_ROLE){
//           return  new ArrayList<>();
//        }
        if(!isAdmin(request)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper= new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            //不写就是模糊查询
            queryWrapper.like("username",username);
        }
       List<User> userList =  userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    }

    @PostMapping ("/delete")
    public boolean deleteUsers(@RequestBody long id,HttpServletRequest request){
        //鉴权 仅管理员可查询 获取登录态
//        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User user = (User) userObject;
//        if (user == null || user.getRole() != ADMIN_ROLE){
//            return  false;
//        }
        if(!isAdmin(request)){
            return false;
        }

        if (id<=0){
            return false;
        }
        //逻辑删除会自动转变为更新 ，M-P官网逻辑删除里面有讲
        return userService.removeById(id);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObject;
//        return user != null && user.getRole() == ADMIN_ROLE;
        if (user == null || user.getUserRole() != ADMIN_ROLE){
            return  false;
        }
        return true;
    }
}
