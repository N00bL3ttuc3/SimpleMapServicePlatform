package com.example.controller;

import cn.hutool.json.JSONObject;
import com.example.annotation.UserLoginToken;
import com.example.common.CodeMsg;
import com.example.common.Result;
import com.example.model.User;
import com.example.service.UserService;
import com.example.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：Mr.ZJW
 * @date ：Created 2022/2/26 10:47
 * @description：
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询用户信息
     * @return
     */
    @UserLoginToken
    @GetMapping("/list")
    public Result<Object> list(){
        return Result.success(userService.list());
    }


    /**
     * 登录验证
     * @param user
     * @param response
     * @return
     */
    @PostMapping("/login")
    public Result<Object> login(@RequestBody User user, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        //获取用户账号密码
        User userForBase = new User();
        User resUser = userService.findByUsername(user);
        userForBase.setId(resUser.getId());
        userForBase.setUsername(resUser.getUsername());
        userForBase.setPassword(resUser.getPassword());
        userForBase.setIdentity(resUser.getIdentity());

        //判断账号或密码是否正确
        if (!userForBase.getPassword().equals(user.getPassword())) {
            return Result.error(CodeMsg.USER_OR_PASS_ERROR);
        } else {
            String token = tokenService.getToken(userForBase);
            jsonObject.put("token", token);
            Cookie cookie = new Cookie("token", token);
            String identity = resUser.getIdentity();
            jsonObject.put("identity",identity);
            cookie.setPath("/");
            response.addCookie(cookie);
            return Result.success(jsonObject);
        }
    }

    @GetMapping("/info/{username}")
    public Boolean getInfo(@PathVariable String username){
        User res = userService.findUserByName(username);
        if (res!=null) return false;
        return true;
    }

    @PostMapping("/register")
    public Boolean register(@RequestBody User input){
        return userService.addUser(input.getUsername(),input.getPassword(),input.getIdentity());
    }
}


