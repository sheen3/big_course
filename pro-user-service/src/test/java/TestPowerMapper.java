import org.database.mysql.domain.Power;
import org.database.mysql.domain.RolePowerRef;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.user.UserApplication;
import org.user.serviceimpl.PowerMapperImpl;

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
public class TestPowerMapper {

    @Autowired
    private PowerMapperImpl powerMapperImpl;



    //增加权限
    @Test
    public void insertPower() throws Exception {
        Power power = new Power();
        power.setPowerId(1049);
        power.setPowerName("角色管理");
        power.setPowerType((short) 0);
        power.setPowerCreateTime(Timestamp.valueOf(LocalDateTime.now()));

        powerMapperImpl.insertPower(power);
        System.out.println("权限创建成功！");
    }

    //根据powerId查询
    @Test
    public void selectOnePower() throws Exception {
        Power power = new Power();
        power.setPowerId(1051);
        powerMapperImpl.selectOnePower(power);
        System.out.println("权限查找成功！");

        System.out.println("############################################################");
        System.out.println("以下是查询所有的权限得到信息:");
        List<Power> powerList = powerMapperImpl.selectAllPower();
        for (Power p : powerList) {
            System.out.println(p);
        }
    }



    //根据powerId删除
    @Test
    public void deletePower() throws Exception {
        Power power = new Power();
        power.setPowerId(1051);
        powerMapperImpl.deletePower(power);
        System.out.println("权限删除成功！");
    }


    //更新权限内容(根据powerId)
    @Test
    public void updatePower() throws Exception {
        Power power = new Power();
        power.setPowerId(3);
        power.setPowerNotes("可操作");
        power.setPowerType((short) 1);
        powerMapperImpl.updatePower(power);
        System.out.println("权限状态已更新！");

    }

    //查询角色对应的权限列表
    @Test
    public void getRolePowers() throws Exception {
        // 执行授权操作
        RolePowerRef rolePowerRef = new RolePowerRef();
        rolePowerRef.setRefRoleId((short) 103);
        List<RolePowerRef> powerList = powerMapperImpl.getRolePowers(rolePowerRef);

        if (powerList != null) {
            System.out.println("角色所属权限查询成功");
        } else {
            System.out.println("此角色无权限");
        }
        System.out.println("############################################################");
        System.out.println("以下是查询到的角色所属权限列表:");
        for (RolePowerRef p : powerList) {
            System.out.println(p);
        }
    }

    //查询权限状态（1为可操作,0为可访问）
    @Test
    public void checkPowerOperate() throws Exception {
        Power power=new Power();
        power.setPowerId(64);
        if(powerMapperImpl.checkPowerOperate(power)){
            System.out.println(power.getPowerId()+"权限状态是可操作");}
        else
        {System.out.println(power.getPowerId()+"权限状态是可访问");}


    }
}
