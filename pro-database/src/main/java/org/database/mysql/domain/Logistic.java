package org.database.mysql.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_logistic")
public class Logistic {
    @TableId

    private String logisticId;
    private String logisticCompanyId;
    private String logisticBatchId;
    private String logisticVehicleInfo;
    private Timestamp logisticTime;
    private String logisticDestinationSupermarket;
}


