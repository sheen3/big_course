package org.database.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.database.mysql.domain.User;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

