package org.user.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiOperation;
import org.database.mysql.domain.Power;
import org.database.mysql.domain.RolePowerRef;
import org.database.mysql.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.user.serviceimpl.PowerMapperImpl;

import java.util.Collections;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@RestController
@RequestMapping("/Power")

public class PowerService {
    private final PowerMapperImpl powerMapperImpl;

    @Autowired
    public PowerService(PowerMapperImpl powerMapperImpl) {
        this.powerMapperImpl = powerMapperImpl;
    }

    //测试sheen
    @GetMapping("/sheen")
    @ApiOperation("测试联通")
    public String get() {
        return "hello sheen!";
    }


    @PostMapping("/powers/insert")
    public void insertPower(@RequestBody Power power) {
        try {
            powerMapperImpl.insertPower(power);

        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //测试查询权限列表信息
    @GetMapping("/powers/All")
    public List<Power> getAllPower() throws Exception {
        try {
            return powerMapperImpl.selectAllPower();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //查找权限，由于权限名可能有多个故只能通过权限id查询
    @PostMapping("/powers/selectOne")
    public void selectOnePower(@RequestBody Power power) throws Exception {
        try {
            powerMapperImpl.selectOnePower(power);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //删除角色
    @DeleteMapping("/powers/delete")
    public void deletePower(@RequestBody Power power) {
        try {
            powerMapperImpl.deletePower(power);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //修改角色信息
    @PostMapping("/power/update")
    public void updatePower(@RequestBody Power power) {
        try {
            powerMapperImpl.updatePower(power);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }


    //查找角色的权限
    @PostMapping("/power/role")
    public List<RolePowerRef> getRolePowers(@RequestBody RolePowerRef rolePowerRef) {
        try {
            return powerMapperImpl.getRolePowers(rolePowerRef);
        } catch (Exception e) {
            System.out.println("获取失败");
            return null;
        }


    }
}