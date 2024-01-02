package org.database.mysql.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role {
    @TableId
    private Short roleId;
    private String roleName;
    private Timestamp roleCreateTime;
    private Short roleStatusFlag;
    private String roleRemark;
}
