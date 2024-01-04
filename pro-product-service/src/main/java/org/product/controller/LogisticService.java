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
    public Boolean insertLogistic(@RequestBody Logistic logistic) {
        try {
            if (logisticMapperImpl.insertLogistic(logistic)) {
                return true;

            }
            return false;

        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }

    //查询全部物流信息
    @GetMapping("/logistic/All")
    public List<Logistic> getAllLogistic() throws Exception {
        try {
            return logisticMapperImpl.selectAllLogistic();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //查询物流信息
    @PostMapping("/logistic/selectOne")
    public Boolean selectOneLogistic(@RequestBody Logistic logistic) throws Exception {
        try {
            if (logisticMapperImpl.selectOneLogistic(logistic)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }

    //删除物流信息
    @DeleteMapping("/logistic/delete")
    public Boolean deleteLogistic(@RequestBody Logistic logistic) {
        try {
            if (logisticMapperImpl.deleteLogistic(logistic)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("操作失败");
        }return null;
    }

    //更新物流信息
    @PostMapping("/logistic/update")
    public Boolean updateLogistic(@RequestBody Logistic logistic) {
        try {
           if( logisticMapperImpl.updateLogistic(logistic)){
               return true;
           }
            return false;
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return null;
    }

    //生成物流和超市订单
    @PostMapping("/logistic/supermarkrt")
    public Boolean sendSupermarket(@RequestBody LogisticsSupermarketRef logisticsSupermarketRef) {
        try {
            if (logisticMapperImpl.sendSupermarket(logisticsSupermarketRef)) {
                System.out.println("物流与超市订单建立成！");
                return true;

            }return false;
        } catch (Exception e) {
            System.out.println("操作失败");
        }return null;
    }
}


