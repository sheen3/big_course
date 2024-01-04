import org.database.mysql.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tools.common.encrypt.PasswordEncrypt;
import org.tools.common.uuid.UuidGenerator;
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
        user.setUserName("kk");
        user.setUserTelephone("15609000234");
        user.setUserSysEmail("kkk@yeah.com");
        user.setUserPassword("kkkv");
        user.setUserNickName("kkkv");
        user.setUserGender("女");
        user.setUserBornDay(Timestamp.valueOf("2001-09-21 00:00:00"));
        user.setUserIdCard("100000001");
        user.setUserCompany("USA.company");
        user.setUserHome("BeiJin");
        user.setUserIp("192.168.1.1");
        user.setUserFlag((short) 1);
        user.setUserPersonalProfile("I am Sheen");
        user.setUserCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        user.setRoleName("系统管理员");
       System.out.println( userMapperimpl.insertUser(user));

    }

    //根据userid或userName、UserTelephone、setUserSysEmail查询
    @Test
    public void selectOneUser() throws Exception {
        User user = new User();
        //user.setUserId("0000018c-9625-4068-9372-b4ad2bac297f");
        user.setUserName("sheen");
        // user.setUserTelephone("17511111111");
        //  user.setUserSysEmail("Fairy@yeah.com");

        System.out.println( userMapperimpl.selectOneUser(user));



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
        user.setUserName("bb");
       // user.setUserId("0000018c-ca65-68f2-b632-6bea9a6c9745");
        System.out.println(   userMapperimpl.deleteUser(user));


    }

    //更新角色内容(根据userId)
    @Test
    public void updateUser() throws Exception {
        User user = new User();
       // user.setUserId("0000018c-5caf-cba7-8b25-8d2a2844cdab");
        user.setUserName("ww");
        //user.setUserSysEmail("Cob@yean.net");
          user.setUserTelephone("12345678457");
        userMapperimpl.updateUser(user);
        System.out.println("角色信息已更新！");
    }

    //验证用户名登陆
    @Test
    public void loginUserByName() throws Exception {
        User user = new User();
        //user.setUserId("0000018c-a3e0-8f55-861b-daa075e0eb38");
        user.setUserName("kk");
        user.setUserPassword("kkkv");
        System.out.println( userMapperimpl.loginUserByName(user));
    }


    //验证用户邮箱登陆
    @Test
    public void loginUserByEmail() throws Exception {
        User user = new User();
      //  user.setUserId("0000018c-a3e0-8f55-861b-daa075e0eb38");
        user.setUserSysEmail("kkk@yeah.com");
        user.setUserPassword("kkkv");
        System.out.println(userMapperimpl.loginUserByEmail(user));
    }

    //验证用户电话登陆
    @Test
    public void loginUserByTelephone() throws Exception {
        User user = new User();
        user.setUserTelephone("15609000234");
        user.setUserPassword("kkikv");
        System.out.println(userMapperimpl.loginUserByTelephone(user));
    }

    //验证用户名登陆
    @Test
    public void isUserWhatToRole() throws Exception {
        User user = new User();
        user.setUserId("0000018c-251b-4676-bd25-17bad9ba3899");
        System.out.println("该用户的角色是:" + userMapperimpl.isUserWhatToRole(user));
    }
}

