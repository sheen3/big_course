package org.user.controller;

import io.swagger.annotations.ApiOperation;
import org.database.mysql.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.user.serviceimpl.UserMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */
@RestController
@RequestMapping("/User")
public class UserController {

    private final UserMapperImpl userMapper;

    @Autowired
    public UserController(UserMapperImpl userMapper) {
        this.userMapper = userMapper;
    }

    //测试sheen
    @GetMapping("/sheen")
    @ApiOperation("测试联通")
    public String get() {
        return "hello sheen!";
    }


    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser() {
        // 处理注册逻辑
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser() {
        // 处理登录逻辑
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        // 显示用户仪表盘页面
        return "dashboard";
    }
}