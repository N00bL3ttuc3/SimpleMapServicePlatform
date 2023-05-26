package com.example.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.UserDao;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int deleteByIds(Long[] ids) {
        return 0;
    }

    @Override
    public Boolean addUser(String username,String password,String identity) {
        return userDao.addUser(username, password, identity);
    }

    /**
     * 判断用户名
     * @param user
     * @return
     */
    public User findByUsername(User user){
        return userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,user.getUsername()));
    }

    public User findUserById(String userId) {
        return userDao.selectById(userId);
    }

    @Override
    public User findUserByName(String username) {

        return userDao.findUserByName(username);
    }

}



