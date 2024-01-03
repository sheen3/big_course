package org.product.controller;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.sequence.InsertCommand;
import org.database.mysql.domain.Product;
import org.database.mysql.domain.Supermarket;
import org.product.serviceimpl.ContaminationMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */

@RestController
@RequestMapping("/Contamination")
public class ContaminationService {
    private final ContaminationMapperImpl contaminationMapperImpl;

    @Autowired
    public ContaminationService(ContaminationMapperImpl contaminationMapperImpl) {
        this.contaminationMapperImpl = contaminationMapperImpl;
    }

    //测试sheen
    @GetMapping("/sheen")
    @ApiOperation("测试联通")
    public String get() {
        return "hello sheen!";
    }

    //添加污染表
    @PostMapping("/contamination/insert")
    public void insertContamination(@RequestBody InsertContaminationRequest request) {
        try {
            contaminationMapperImpl.insertContamination(request.getProduct(), request.getDescription(), request.getStatus());
        } catch (Exception e) {
            // 处理异常
        }
    }

    static class InsertContaminationRequest {
        private Product product;
        private String description;
        private short status;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public short getStatus() {
            return status;
        }

        public void setStatus(short status) {
            this.status = status;
        }
    }
}