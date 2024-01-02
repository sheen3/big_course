package org.database.mysql.entity;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */


public enum MysqlResultType {
    //一个
    ONE,
    // 多个
    LIST,
    // 无
    NULL
}
//mysql查询结果的类型
//使用MysqlResultType.ONE表示查询结果只有一个