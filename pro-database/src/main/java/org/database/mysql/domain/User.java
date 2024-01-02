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
@TableName("sys_user")
public class User {
    @TableId
    private String userId;
    private String userName;
    private String userTelephone;
    private String userSysEmail;
    private String userPassword;
    private String userNickName;
    private String userGender;
    private Timestamp userBornDay;
    private String userIdCard;
    private String userCompany;
    private String userHome;
    private String userIp;
    private Short userFlag;
    private String userPersonalProfile;
    private Timestamp userCreateTime;
    private String roleName;
}
