package org.product.controller;

import io.swagger.annotations.ApiOperation;
import org.database.mysql.domain.LogisticsSupermarketRef;
import org.database.mysql.domain.Product;
import org.database.mysql.domain.Supermarket;
import org.product.serviceimpl.SupermarketMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@RestController
@RequestMapping("/Supermarket")
public class SupermarketService {
    private final SupermarketMapperImpl supermarketMapperImpl;

    @Autowired

    public SupermarketService(SupermarketMapperImpl supermarketMapperImpl) {
        this.supermarketMapperImpl = supermarketMapperImpl;
    }

    //测试sheen
    @GetMapping("/sheen")
    @ApiOperation("测试联通")
    public String get() {
        return "hello sheen!";
    }

    //添加超市
    @PostMapping("/supermarket/insert")
    public Boolean insertSupermarket(@RequestBody Supermarket supermarket) {
        try {
           if(supermarketMapperImpl.insertSupermarket(supermarket)){
               return true;
           }return false;

        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }

    //查询全部产品信息
    @GetMapping("/supermarket/All")
    public List<Supermarket> getAllSupermarket() throws Exception {
        try {
            return supermarketMapperImpl.selectAllSupermarket();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //查询超市信息
    @PostMapping("/supermarket/selectOne")
    public Supermarket selectOneSupermarket(@RequestBody Supermarket supermarket) throws Exception {
        try {
            return supermarketMapperImpl.selectOneSupermarket(supermarket);
        } catch (Exception e) {
            System.out.println("操作失败");
        }return null;
    }

    //删除超市
    @DeleteMapping("/supermarket/delete")
    public void deleteSupermarket(@RequestBody Supermarket supermarket) {
        try {
            supermarketMapperImpl.deleteSupermarket(supermarket);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //更新超市信息
    @PostMapping("/supermarket/update")
    public Boolean updateSupermarket(@RequestBody Supermarket supermarket) {
        try {
            if(supermarketMapperImpl.updateSupermarket(supermarket)){
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }

    //商品入库
    @PostMapping("/supermarket/getProduct")
    public Boolean getProduct(@RequestBody LogisticsSupermarketRef logisticsSupermarketRef) {
        try {
            if(supermarketMapperImpl.getProduct(logisticsSupermarketRef)){
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }

    //查询是否有产品污染情况
    @PostMapping("/supermarket/conProduct")
    public Product supermarketGetCon(@RequestBody Product product) {
        try {
            return supermarketMapperImpl.supermarketGetCon(product);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }

}
