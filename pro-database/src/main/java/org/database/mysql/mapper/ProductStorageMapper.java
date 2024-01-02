package org.database.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.database.mysql.domain.ProductStorage;
import org.database.mysql.domain.Supermarket;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */
@Mapper
public interface ProductStorageMapper extends BaseMapper<ProductStorage> {
}

