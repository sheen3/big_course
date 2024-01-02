package org.database.mysql.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_product")
public class Product {
    @TableId
    private String productId;
    private String productName;
    private Date productDate;
    private Date productExpirationDate;
    private String productEnterpriseId;
    private String productProductionId;
    private String productionPlace;
}
