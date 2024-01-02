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
@TableName("sys_qr_code")
public class QrCode {
    @TableId
    private String qrCodeId;
    private String qrCodeContent;
    private String productId;
    private String logisticId;
}

