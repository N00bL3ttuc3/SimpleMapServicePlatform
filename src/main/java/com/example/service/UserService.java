package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.User;

public interface UserService extends IService<User> {


    int deleteByIds(Long[] ids);

    Boolean addUser(String username,String password,String identity);

    User findByUsername(User user);

    User findUserById(String userId);

    User findUserByName(String username);
}



