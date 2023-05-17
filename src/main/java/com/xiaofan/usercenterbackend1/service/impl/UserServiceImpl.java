package com.xiaofan.usercenterbackend1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaofan.usercenterbackend1.model.domain.User;
import com.xiaofan.usercenterbackend1.service.UserService;
import com.xiaofan.usercenterbackend1.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 小fan
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-05-16 21:17:49
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }
        if (userAccount.length()<4){
            return -1;
        }
        if (userPassword.length()<8 || checkPassword.length()<8){
            return -1;
        }

        //账户不能包含特殊字符
        String regex = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(regex).matcher(userAccount);
        if (matcher.find()){
            return -1;
        }

        //密码和校验密码相同
      if (!userPassword.equals(checkPassword)){
          return -1 ;
      }


        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0){
            return -1;
        }

        //2.加密
        final String SALT = "fantuan";
        String encryptPassword  = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult){
            return  -1;
        }
        return user.getId();
    }
}




