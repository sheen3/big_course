package org.product.controller;

import io.swagger.annotations.ApiOperation;
import org.database.mysql.domain.Logistic;
import org.database.mysql.domain.LogisticsSupermarketRef;
import org.database.mysql.domain.Product;
import org.product.serviceimpl.LogisticMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@RestController
@RequestMapping("/Logistic")
public class LogisticService {
    private final LogisticMapperImpl logisticMapperImpl;
    @Autowired

    public LogisticService(LogisticMapperImpl logisticMapperImpl) {
        this.logisticMapperImpl = logisticMapperImpl;
    }

    //测试sheen
    @GetMapping("/sheen")
    @ApiOperation("测试联通")
    public String get() {
        return "hello sheen!";
    }

    //产生运输信息
    @PostMapping("/logistic/insert")
    public void insertLogistic(@RequestBody Logistic logistic) {
        try {
            logisticMapperImpl.insertLogistic(logistic);

        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //查询全部物流信息
    @GetMapping("/logistic/All")
    public List<Logistic> getAllLogistic() throws Exception{
        try {
            return logisticMapperImpl.selectAllLogistic();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    //查询物流信息
    @PostMapping("/logistic/selectOne")
    public void selectOneLogistic(@RequestBody Logistic logistic) throws Exception {
        try {
            logisticMapperImpl.selectOneLogistic(logistic);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }
    //删除物流信息
    @DeleteMapping("/logistic/delete")
    public void deleteLogistic(@RequestBody Logistic logistic) {
        try {
            logisticMapperImpl.deleteLogistic(logistic);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }
    //更新物流信息
    @PostMapping("/logistic/update")
    public void updateLogistic(@RequestBody Logistic logistic) {
        try {
            logisticMapperImpl.updateLogistic(logistic);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }
    //生成物流和超市订单
    @PostMapping("/logistic/supermarkrt")
    public void sendSupermarket(@RequestBody LogisticsSupermarketRef logisticsSupermarketRef) {
        try {
            if( logisticMapperImpl.sendSupermarket(logisticsSupermarketRef)) {
                System.out.println("物流与超市订单建立成！");
            }
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }
}


