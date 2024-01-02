package org.database.mysql.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_logistics_supermarket_ref")
public class LogisticsSupermarketRef {
    @TableId(type = IdType.AUTO)
    private Integer refId;
    private String logisticId;
    private String supermarketId;
}