package org.database.mysql.domain;


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
@TableName("sys_contamination_records")
public class ContaminationRecord {
    @TableId
    private String recordId;
    private String productId;
    private String contaminationDescription;
    private Byte recordsStatus;
}


