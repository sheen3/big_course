package org.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */
@SpringBootApplication(scanBasePackages = {"org.database", "org.user"})
@SpringBootConfiguration
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}