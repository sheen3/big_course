package org.user.controller;

import io.swagger.annotations.ApiOperation;
import org.database.mysql.domain.Power;
import org.database.mysql.domain.Role;
import org.database.mysql.domain.RolePowerRef;
import org.database.mysql.domain.UserRoleRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.user.serviceimpl.PowerMapperImpl;
import org.user.serviceimpl.RoleMapperImpl;

import java.util.Collections;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@RestController
@RequestMapping("/Role")
public class RoleService {
    private final RoleMapperImpl roleMapperImpl;

    @Autowired
    public RoleService(RoleMapperImpl roleMapperImpl) {
        this.roleMapperImpl = roleMapperImpl;
    }

    //测试sheen
    @GetMapping("/sheen")
    @ApiOperation("测试联通")
    public String get() {
        return "hello sheen!";
    }

    // 增加角色
    @PostMapping("/role/insert")
    public Boolean insertRole(@RequestBody Role role) {
        try {
            if (roleMapperImpl.insertRole(role)) {
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }

    //测试查询权限列表信息
    @GetMapping("/roles/All")
    public List<Role> getAllRole() throws Exception {
        try {
            return roleMapperImpl.selectAllRole();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //查找权限，由于权限名可能有多个故只能通过权限id查询
    @PostMapping("/roles/selectOne")
    public Boolean selectOneRole(@RequestBody Role role) throws Exception {
        try {
            if (roleMapperImpl.selectOneRole(role)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }

    //删除角色
    @DeleteMapping("/roles/delete")
    public Boolean deleteRole(@RequestBody Role role) {
        try {
            if (roleMapperImpl.deleteRole(role)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }

    //修改角色信息
    @PostMapping("/role/update")
    public Boolean updateRole(@RequestBody Role role) {
        try {
            if (roleMapperImpl.updateRole(role)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }


    //分配角色给用户
    @PostMapping("/role/user")
    public Boolean grantRoleToUser(UserRoleRef userRoleRef) {
        try {
            if (roleMapperImpl.revokeRoleFromUser(userRoleRef)) {
                System.out.println("撤销角色给用户成功！");
                return true;
            } else {
                System.out.println("撤销角色给用户失败！");
            }
        } catch (Exception e) {

            System.out.println("操作失败");

        }
        return false;
    }

    //撤销已分配权限给角色
    @PostMapping("/role/revokePower")
    public Boolean revokePowerFromRole(RolePowerRef rolePowerRef) {
        try {
            if (roleMapperImpl.revokePowerFromRole(rolePowerRef)) {
                System.out.println("撤销权限给角色成功！");
                return true;
            } else {
                System.out.println("撤销权限给角色失败！");
            }
        } catch (Exception e) {
            System.out.println("操作失败");

        }
        return null;
    }


}


