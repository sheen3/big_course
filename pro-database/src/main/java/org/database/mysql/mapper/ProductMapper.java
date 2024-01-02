package org.database.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.database.mysql.domain.Power;
import org.database.mysql.domain.Product;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
