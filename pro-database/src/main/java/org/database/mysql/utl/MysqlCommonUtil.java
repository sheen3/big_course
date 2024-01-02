package org.database.mysql.utl;

import org.database.mysql.domain.Power;
import org.database.mysql.domain.Product;
import org.database.mysql.domain.Role;
import org.database.mysql.domain.User;
import org.springframework.stereotype.Component;
import org.tools.exception.FormatException;

import java.lang.reflect.Field;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */

@Component
public class MysqlCommonUtil {

    /**
     * 将java对象的字段名转换成mysql的列名
     * 接受一个java类的字段名作为参数，并返回对应的MYSQL列名
     * 会根据字段名中的字符大小写情况进行转换
     * 将大写字母转换为下划线家小写字母的形式
     * javaFieldName2MysqlColumnsName("userName)
     * 将返回user_name
     * 如果字段名不符合命名规范，将抛出FormatException异常
     *
     * @param fieldName java类的字段名
     * @return mysql的列名
     */
    public String javaFieldName2MysqlColumnsName(String fieldName) throws FormatException {
        StringBuilder mysqlName = new StringBuilder();
        int len = fieldName.length();
        StringBuilder temp = new StringBuilder();
        for (int index = 0; index < len; index++) {
            char s = fieldName.charAt(index);
            int i = checkLetterUpperOrLower(s);
            switch (i) {
                // 大写的话 将之前的给加到结果当中
                case 0: {
                    if (temp.length() == 0) {
                        continue;
                    }
                    mysqlName.append(temp.append("_"));
                    temp = new StringBuilder();
                    temp.append(Character.toLowerCase(s));
                    break;
                }
                case 1: {
                    temp.append(Character.toLowerCase(s));
                    if (index + 1 == len) {
                        mysqlName.append(temp);
                        break;
                    }
                    break;
                }
                case -1:
                default:
                    throw new FormatException("javaFieldName2MysqlColumnsName");
            }
        }
        return mysqlName.toString();
    }

    /**
     * 判断字母是大写还是小写  大写返回0  小写返回1 不是字母则返回-1
     *
     * @param letter 字母
     * @return
     */
    public int checkLetterUpperOrLower(char letter) {
        if (Character.isUpperCase(letter)) {
            return 0;
        }
        if (Character.isLowerCase(letter)) {
            return 1;
        }
        return -1;
    }

    /**
     * 先构建了一个MysqlCommonUtil对象,然后获取了Announcement类的所有字段
     * 遍历每个字段然后调用javaFieldName2MysqlColumnsName方法将字段名转换为MySQL列名，并打印结果
     *
     * @return
     */
    public static void main(String[] args) throws FormatException {
        MysqlCommonUtil mysqlCommonUtil = new MysqlCommonUtil();

        Class<User> clz1 = User.class;
        Field[] fields1 = clz1.getDeclaredFields();
        for (Field field : fields1) {
            System.out.println(field.getName() + "--->" + mysqlCommonUtil.javaFieldName2MysqlColumnsName(field.getName()));
        }

        Class<Role> clz2 = Role.class;
        Field[] fields2=clz2.getDeclaredFields();
        for (Field field : fields2) {
            System.out.println(field.getName() + "--->" + mysqlCommonUtil.javaFieldName2MysqlColumnsName(field.getName()));
        }

        Class<Power> clz3 = Power.class;
        Field[] fields3=clz3.getDeclaredFields();
        for (Field field : fields3) {
            System.out.println(field.getName() + "--->" + mysqlCommonUtil.javaFieldName2MysqlColumnsName(field.getName()));
        }

        Class<Product> clz4 = Product.class;
        Field[] fields4=clz4.getDeclaredFields();
        for (Field field : fields4) {
            System.out.println(field.getName() + "--->" + mysqlCommonUtil.javaFieldName2MysqlColumnsName(field.getName()));
        }

    }

}
