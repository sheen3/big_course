package org.database.mysql.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_power_ref")
public class RolePowerRef {
    @TableId(type = IdType.AUTO)

    private Integer refId;
    private Short refRoleId;
    private Integer refPowerId;
}
