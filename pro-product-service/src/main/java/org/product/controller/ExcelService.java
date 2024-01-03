package org.product.controller;

import org.database.mysql.domain.QrCode;
import org.product.serviceimpl.ExcelGetImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tools.Excel.ExcelReader;

import java.util.Collections;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@RestController
@RequestMapping("/Excel")
public class ExcelService {
    //拿到产品报表
    @GetMapping("/excel/ProductExcelGet")
    public void ProductExcelGet() throws Exception {
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_product.xlsx";
            excelReader.readFromExcel(excelFilePath);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }
    //拿到物流报表
    @GetMapping("/excel/LogisticExcelGet")
    public void LogisticExcelGet() throws Exception {
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_Logistic.xlsx";
            excelReader.readFromExcel(excelFilePath);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

    //拿到超市报表
    @GetMapping("/excel/SupermarketExcelGet")
    public void SupermarketExcelGet() throws Exception {
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_supermarket.xlsx";
            excelReader.readFromExcel(excelFilePath);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }
    //拿到污染表
    @GetMapping("/excel/ContaminationExcelGet")
    public void ContaminationExcelGet() throws Exception {
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_Con.xlsx";
            excelReader.readFromExcel(excelFilePath);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
    }

}
