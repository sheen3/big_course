import org.database.mysql.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.user.UserApplication;
import org.user.serviceimpl.UserMapperImpl;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */


@SpringBootTest(classes = UserApplication.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class TestUserMapper {

    @Autowired
    private UserMapperImpl userMapperimpl;

    @Test
    public void insertUser() throws Exception {

        User user = new User();
        //  user.setUserId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
        user.setUserName("owm");
        user.setUserTelephone("15679090001");
        user.setUserSysEmail("owm.com");
        user.setUserPassword("owm");
        user.setUserNickName("mwo");
        user.setUserGender("女");
        user.setUserBornDay(Timestamp.valueOf("2001-09-21 00:00:00"));
        user.setUserIdCard("100000002");
        user.setUserCompany("USA.company");
        user.setUserHome("BeiJin");
        user.setUserIp("192.168.1.1");
        user.setUserFlag((short) 1);
        user.setUserPersonalProfile("I am Sheen");
        user.setUserCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        user.setRoleName("质监部门");
        if (userMapperimpl.insertUser(user)) {
            System.out.println("注册成功");
        } else System.out.println("注册失败");

    }

    //根据userid或userName、UserTelephone、setUserSysEmail查询
    @Test
    public void selectOneUser() throws Exception {
        User user = new User();
        //user.setUserId("0000018c-9625-4068-9372-b4ad2bac297f");
        user.setUserName("owm");
        // user.setUserTelephone("17511111111");
        //  user.setUserSysEmail("Fairy@yeah.com");

        System.out.println(userMapperimpl.selectOneUser(user));


        System.out.println("############################################################");
        System.out.println("以下是查询所有的权限得到信息:");
        List<User> userList = userMapperimpl.selectAllUser();
        for (User p : userList) {
            System.out.println(p);
        }

    }

    //删除角色
    @Test
    public void deleteRole() throws Exception {
        User user = new User();
        user.setUserName("owm");
        // user.setUserId("0000018c-ca65-68f2-b632-6bea9a6c9745");
        if (userMapperimpl.deleteUser(user)) {
            System.out.println("注销成功");
        } else {
            System.out.println("注销失败");
        }


    }

    //更新角色内容(根据userId)
    @Test
    public void updateUser() throws Exception {
        User user = new User();
        // user.setUserId("0000018c-5caf-cba7-8b25-8d2a2844cdab");
        user.setUserName("kkk");
        //user.setUserSysEmail("Cob@yean.net");
        user.setUserTelephone("15679090009");
        if (userMapperimpl.updateUser(user)) {
            System.out.println("角色信息已更新！");
        }
        System.out.println("角色信息更新失败！");

    }

    //验证用户名登陆
    @Test
    public void loginUserByName() throws Exception {
        User user = new User();
        //user.setUserId("0000018c-a3e0-8f55-861b-daa075e0eb38");
        user.setUserName("owm");
        user.setUserPassword("owm");
        if (userMapperimpl.loginUserByName(user)) {
            System.out.println("登陆成功");
        } else {
            System.out.println("登陆失败");
        }
    }


    //验证用户邮箱登陆
    @Test
    public void loginUserByEmail() throws Exception {
        User user = new User();
        //  user.setUserId("0000018c-a3e0-8f55-861b-daa075e0eb38");
        user.setUserSysEmail("kkk@yeah.com");
        user.setUserPassword("kkkv");
        if(userMapperimpl.loginUserByEmail(user)){
            System.out.println("登陆成功");
        } else {
            System.out.println("登陆失败");
        }
    }

    //验证用户电话登陆
    @Test
    public void loginUserByTelephone() throws Exception {
        User user = new User();
        user.setUserTelephone("15609000234");
        user.setUserPassword("kkikv");
        if(userMapperimpl.loginUserByTelephone(user)) {
            System.out.println("登陆成功");
        } else {
            System.out.println("登陆失败");
        }
    }

    //验证用户的角色
    @Test
    public void isUserWhatToRole() throws Exception {
        User user = new User();
        user.setUserId("0000018c-d33a-ca76-a9d7-d80751935e3c");
        System.out.println("该用户的角色是:" + userMapperimpl.isUserWhatToRole(user));
    }
}

