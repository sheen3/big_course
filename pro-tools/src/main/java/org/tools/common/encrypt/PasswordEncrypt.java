package org.tools.common.encrypt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @description: 给密码加密 不可逆
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */
public class PasswordEncrypt {

    private static final Logger logger = LogManager.getLogger(PasswordEncrypt.class);
    private static final byte[] SALT = "[-116, -118, 25, -39, 85, -39, -127, -110, 54, 61, -100, -1, 87, -68, 48, 52]".getBytes();


    /**
     * @decription: 生成随机的盐值
     * @return
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }


    /**
     * @decription: 使用SHA-256哈希算法对密码和盐值进行哈希
     * @param password
     * @return
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(SALT);
            byte[] hashedPassword = md.digest(password.getBytes());

            // 将盐值和哈希后的密码转换为Base64编码的字符串存储
            return Base64.getEncoder().encodeToString(SALT) + ":" + Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            logger.error("No Such AlgorithmException:"+e);
            return null;
        }
    }


}
