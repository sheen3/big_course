package org.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/4
 */
@SpringBootApplication(scanBasePackages = {"org.database", "org.user","org.tools","org.start","org.product"})
@SpringBootConfiguration
public class StartApplication { public static void main(String[] args) {
    SpringApplication.run(StartApplication.class, args);
}
}
