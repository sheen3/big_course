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
    public Boolean insertUser(@RequestBody User user) {
        try {
            if (userMapperImpl.insertUser(user)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("注册失败");

        }
        return false;
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
    public Boolean selectOneUser(@RequestBody User user) throws Exception {
        try {
            if (userMapperImpl.selectOneUser(user) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return false;
    }

    //删除用户
    @DeleteMapping("/users/delete")
    public Boolean deleteUser(@RequestBody User user) {
        try {
            if (userMapperImpl.deleteUser(user)) {
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return false;
    }

    //修改用户信息
    @PostMapping("/users/update")
    public Boolean updateUser(@RequestBody User user) {
        try {
            if (userMapperImpl.updateUser(user)) {
                return true;
            }
            return false;

        } catch (Exception e) {

        }
        return false;
    }

    @PostMapping("/loginByUserName")
    public Boolean loginUserByName(@RequestBody User user) {
        try {
            if (userMapperImpl.loginUserByName(user)) {
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    @PostMapping("/loginByUserTelephone")
    public Boolean loginUserByTelephone(@RequestBody User user) {
        try {
            if (userMapperImpl.loginUserByTelephone(user)) {
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    @PostMapping("/loginByUserEmail")
    public Boolean loginUserByEmail(@RequestBody User user) {
        try {
            if (userMapperImpl.loginUserByEmail(user)) {
                return true;
            }
            return false;
        } catch (Exception e) {

        }
        return false;

    }

    //查看当前用户角色
    @PostMapping("/user/role")
    public Boolean isUserWhatToRole(@RequestBody User user) {
        try {
            if (userMapperImpl.isUserWhatToRole(user) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {

        }
        return false;
    }


}




