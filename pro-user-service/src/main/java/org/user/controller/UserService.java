package org.user.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.user.serviceimpl.PowerMapperImpl;
import org.user.serviceimpl.RoleMapperImpl;
import org.user.serviceimpl.UserMapperImpl;
import org.database.mysql.domain.User;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@RestController
@RequestMapping("/UserService")
public class UserService {
    private final UserMapperImpl userMapperImpl;


    @Autowired
    public UserService(UserMapperImpl userMapperImpl) {
        this.userMapperImpl = userMapperImpl;

    }

    //测试sheen
    @GetMapping("/sheen")
    @ApiOperation("测试联通")
    public String get() {
        return "hello sheen!";
    }

    @PostMapping("/users/insert")
    public void insertUser(@RequestBody User user) {
        try {
            userMapperImpl.insertUser(user);

        } catch (Exception e) {
            System.out.println("注册失败");
        }
    }

    //测试查询用户列表信息
    @GetMapping("/users/All")
    public List<User> getAllUser() throws Exception {
        try {
            return userMapperImpl.selectAllUser();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //用户id 或者用户名 或者用户邮箱 或者用户电话 查询用户列表信息
    @PostMapping("/users/selectOne")
    public void selectOneUser(@RequestBody User user) throws Exception {
        try {
            userMapperImpl.selectOneUser(user);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //删除用户
    @DeleteMapping("/users/delete")
    public void deleteUser(@RequestBody User user) {
        try {
            userMapperImpl.deleteUser(user);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //修改用户信息
    @PostMapping("/users/update")
    public void updateUser(@RequestBody User user) {
        try {
            userMapperImpl.updateUser(user);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    @PostMapping("/loginByUserName")
    public boolean loginUserByName(@RequestBody User user) {
        try {
            return userMapperImpl.loginUserByName(user);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/loginByUserTelephone")
    public boolean loginUserByTelephone(@RequestBody User user) {
        try {
            return userMapperImpl.loginUserByTelephone(user);
        } catch (Exception e) {
            return false;
        }

    }

    @PostMapping("/loginByUserEmail")
    public boolean loginUserByEmail(@RequestBody User user) {
        try {
            return userMapperImpl.loginUserByEmail(user);
        } catch (Exception e) {
            return false;
        }

    }

    //查看当前用户角色
    @PostMapping("/user/role")
    public Short isUserWhatToRole(@RequestBody User user) {
        try {
            return userMapperImpl.isUserWhatToRole(user);
        } catch (Exception e) {
            return null;
        }
    }


}




