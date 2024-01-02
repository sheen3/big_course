package org.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */
@SpringBootApplication(scanBasePackages = {"org.database", "org.product"})
@SpringBootConfiguration
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
