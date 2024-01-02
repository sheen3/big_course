package org.database.mysql.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.database.mysql.BaseMysqlComp;
import org.database.mysql.domain.User;
import org.database.mysql.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */

@Component
public class UserMysqlComp {

    private final BaseMysqlComp mysqlComp;

    private final UserMapper userMapper;

    public UserMysqlComp(BaseMysqlComp mysqlComp) {
        this.mysqlComp = mysqlComp;
       this.userMapper = mysqlComp.getUserMapper();
    }

    /**
     * 通过电话、邮箱、用户名判断用户是否存在
     *
     * @param key 电话|邮箱|用户名
     * @return 是否存在
     */
    public boolean checkUserIsExist(String key) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, key)
                .or().eq(User::getUserTelephone, key)
                .or().eq(User::getUserSysEmail, key);
        Integer count = userMapper.selectCount(wrapper);
        return count > 0;
    }

}
