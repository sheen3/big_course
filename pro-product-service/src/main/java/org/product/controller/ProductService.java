package org.product.controller;

import io.swagger.annotations.ApiOperation;
import org.database.mysql.domain.Power;
import org.database.mysql.domain.Product;
import org.product.serviceimpl.ProductMapperImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */

@RestController
@RequestMapping("/Product")
public class ProductService {
    private final ProductMapperImpl productMapperImpl;

    public ProductService(ProductMapperImpl productMapperImpl) {
        this.productMapperImpl = productMapperImpl;
    }

    //测试sheen
    @GetMapping("/sheen")
    @ApiOperation("测试联通")
    public String get() {
        return "hello sheen!";
    }

    //生产产品
    @PostMapping("/products/insert")
    public void insertProduct(@RequestBody Product product) {
        try {
            productMapperImpl.insertProduct(product);

        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //查询全部产品信息
    @GetMapping("/product/All")
    public List<Product> getAllProduct() throws Exception {
        try {
            return productMapperImpl.selectAllProduct();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //查询全部产品信息
    @PostMapping("/product/selectOne")
    public void selectOneProduct(@RequestBody Product product) throws Exception {
        try {
            productMapperImpl.selectOneProduct(product);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //销毁产品
    @DeleteMapping("/product/delete")
    public void deleteProduct(@RequestBody Product product) {
        try {
            productMapperImpl.deleteProduct(product);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //更新产品内容
    @PostMapping("/product/update")
    public void updateProduct(@RequestBody Product product) {
        try {
            productMapperImpl.updateProduct(product);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

}

