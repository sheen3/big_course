import org.database.mysql.domain.Role;
import org.database.mysql.domain.RolePowerRef;
import org.database.mysql.domain.UserRoleRef;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.user.UserApplication;
import org.user.serviceimpl.RoleMapperImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */

@SpringBootTest(classes = UserApplication.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class TestRoleMapper {

    @Autowired
    private RoleMapperImpl roleMapperImpl;

    //增加角色
    @Test
    public void insertRole() throws Exception {
        Role role = new Role();
        //   role.setRoleId((short) 101);
        role.setRoleName("维护者");
        role.setRoleCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        role.setRoleStatusFlag((short) 0);
        role.setRoleRemark("销售设备");

        if (roleMapperImpl.insertRole(role)) {
            System.out.println("角色创建成功！");
        } else {
            System.out.println("角色创建失败！");
        }
    }

    //根据roleId或roleName查询
    @Test
    public void selectOneRole() throws Exception {
        Role role = new Role();
        // role.setRoleId((short)101);
        role.setRoleName("维护者");
        if (roleMapperImpl.selectOneRole(role) != null) {
            System.out.println("权限查找成功！");
        } else {
            System.out.println("权限查找失败！");
        }

        System.out.println("############################################################");
        System.out.println("以下是查询所有的权限得到信息:");
        List<Role> roleList = roleMapperImpl.selectAllRole();
        for (Role p : roleList) {
            System.out.println(p);
        }
        System.out.println("以下是查询所有的可用权限得到信息:");
        List<Role> role2List = roleMapperImpl.selectAllDoRole();
        for (Role p : role2List) {
            System.out.println(p);
        }
    }

    //删除角色
    @Test
    public void deleteRole() throws Exception {
        Role role = new Role();
        //role.setRoleId((short) 104);
        role.setRoleName("维护者");
        if (roleMapperImpl.deleteRole(role)) {
            System.out.println("角色删除成功！");
        } else System.out.println("角色删除失败！");
    }

    @Test
    public void updateRole() throws Exception {
        Role role = new Role();
        role.setRoleId((short) 6);
        role.setRoleRemark("维修");
        if (roleMapperImpl.updateRole(role)) {
            System.out.println("角色状态已更新！");
        } else {
            System.out.println("角色状态更新失败");
        }

    }

    //分配角色给用户
    @Test
    public void grantUserToRole() throws Exception {
        UserRoleRef userRoleRef = new UserRoleRef();
        userRoleRef.setRefUserId("0000018c-d2a1-2612-be6c-95280b010e9d");
        userRoleRef.setRefRoleId((short) 1);
        // 执行授权并验证授权结果
        if (roleMapperImpl.grantRoleToUser(userRoleRef)) {
            System.out.println("分配角色给用户成功！");
        } else {
            System.out.println("分配角色给用户失败！");
        }
    }

    //撤销角色给用
    @Test
    public void revokeRoleFromUser() throws Exception {
        UserRoleRef userRoleRef = new UserRoleRef();
        userRoleRef.setRefUserId("0000018c-d2a1-2612-be6c-95280b010e9d");
        userRoleRef.setRefRoleId((short) 1);
        // 执行撤销并验证授权结果
        if (roleMapperImpl.revokeRoleFromUser(userRoleRef)) {
            System.out.println("撤销角色给用户成功！");
        } else {
            System.out.println("撤销角色给用户失败！");
        }
    }


    //分配可操作权限给角色
    @Test
    public void grantDoPowerToRole() throws Exception {
        RolePowerRef rolePowerRef = new RolePowerRef();
        rolePowerRef.setRefPowerId(1);
        rolePowerRef.setRefRoleId((short) 1);
        // 执行授权并验证授权结果
        if (roleMapperImpl.grantDoPowerToRole(rolePowerRef)) {
            System.out.println("分配可操作权限给角色成功！");
        } else {
            System.out.println("分配可操作权限给角色失败！");
        }
    }

    //分配可查看权限给角色
    @Test
    public void grantSeePowerToRole() throws Exception {
        RolePowerRef rolePowerRef = new RolePowerRef();
        rolePowerRef.setRefPowerId(1);
        rolePowerRef.setRefRoleId((short) 1);
        // 执行授权并验证授权结果
        if (roleMapperImpl.grantSeePowerToRole(rolePowerRef)) {
            System.out.println("分配可查看作权限给角色成功！");
        } else {
            System.out.println("分配查看权限给角色失败！");
        }
    }

    //撤销已分配权限给角色
    @Test
    public void revokePowerFromRole() throws Exception {
        RolePowerRef rolePowerRef = new RolePowerRef();
        rolePowerRef.setRefPowerId(1);
        rolePowerRef.setRefRoleId((short) 1);
        // 执行撤销并验证授权结果
        if (roleMapperImpl.revokePowerFromRole(rolePowerRef)) {
            System.out.println("撤销权限给角色成功！");
        } else {
            System.out.println("撤销权限给角色失败！");
        }
    }


    //查询角色状态
    @Test
    public void checkRoleOperate() throws Exception {
        Role role = new Role();
        role.setRoleId((short) 105);
        if (roleMapperImpl.checkRoleOperate(role)) {
            System.out.println(role.getRoleId() + "状态是正常使用");
        } else {
            System.out.println(role.getRoleId() + "状态是已停用");

        }

    }


}




    /*



    //判断用户的角色
    @Test
    public void isUserWhatToRole() {
        String userId = "0000018b-e2b0-2c75-988b-44cac59dd328";
        String roleName = roleMapperImpl.isUserWhatToRole(userId);
        System.out.println(userId+"的角色是:"+roleName);
    }


*/
