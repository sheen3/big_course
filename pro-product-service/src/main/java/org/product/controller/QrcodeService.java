package org.product.controller;

import io.swagger.annotations.ApiOperation;
import org.database.mysql.domain.*;
import org.product.serviceimpl.QrCodeMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@RestController
@RequestMapping("/Qrcode")
public class QrcodeService {
    private final QrCodeMapperImpl qrCodeMapperImpl;

    @Autowired
    public QrcodeService(QrCodeMapperImpl qrCodeMapperImpl) {
        this.qrCodeMapperImpl = qrCodeMapperImpl;
    }

    //测试sheen
    @GetMapping("/sheen")
    @ApiOperation("测试联通")
    public String get() {
        return "hello sheen!";
    }


    //查询全部二维码
    @GetMapping("/qrCode/All")
    public List<QrCode> getAllQrCode() throws Exception {
        try {

            return qrCodeMapperImpl.selectAllQrCode();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //查询二维码
    @PostMapping("/QrCode/selectOne")
    public void selectOneQrCode(@RequestBody QrCode qrCode) throws Exception {
        try {
            qrCodeMapperImpl.selectOneQrCode(qrCode);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //删除二维码信息
    //可通过qrcodeId、productId、logisticId
    @DeleteMapping("/qrCode/delete")
    public void deleteQrCode(@RequestBody QrCode qrCode) {
        try {
            qrCodeMapperImpl.deleteQrCode(qrCode);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //扫描产品二维码
    @PostMapping("/qrCode/scanProductQrCode")
    public void scanProductQrCode(@RequestBody Product product) {
        try {
            qrCodeMapperImpl.sanProductQrCode(product);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //扫描物流二维码
    @PostMapping("/qrCode/scanLogisticQrCode")
    public void scanLogisticQrCode(@RequestBody Logistic logistic) {
        try {
            qrCodeMapperImpl.sanLogisticQrCode(logistic);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //打包张贴两张二维码
    @PostMapping("/qrCode/packProduct")
    public void supermarketGetCon(@RequestBody ProductLogisticRef productLogisticRef) {
        try {
            qrCodeMapperImpl.packProduct(productLogisticRef);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

}

