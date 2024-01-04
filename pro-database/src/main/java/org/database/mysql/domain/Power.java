package org.database.mysql.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("sys_power")
public class Power {
    @TableId(type = IdType.AUTO)
    private Integer powerId;
    private String powerName;
    private Short powerType;
    private Timestamp powerCreateTime;
    private String powerNotes;

}
