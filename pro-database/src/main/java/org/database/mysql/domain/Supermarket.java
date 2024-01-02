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
@TableName("sys_supermarket")
public class Supermarket {
    @TableId
    private String supermarketId;
    private String supermarketName;
    private String supermarketAddress;
    private String supermarketContact;
}