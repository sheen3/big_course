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
import java.util.Objects;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@RestController
@RequestMapping("/Excel")
public class ExcelService {
    //拿到产品报表
    @GetMapping("/excel/ProductExcelGet")
    public List<List<Object>> ProductExcelGet() throws Exception {
        List<List<Object>> excelData = null;
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_product.xlsx";
            excelData = excelReader.readFromExcel(excelFilePath);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return excelData;
    }

    //拿到物流报表
    @GetMapping("/excel/LogisticExcelGet")
    public List<List<Object>> logisticExcelGet() throws Exception {
        List<List<Object>> excelData = null;
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_Logistic.xlsx";
            excelData = excelReader.readFromExcel(excelFilePath);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return excelData;
    }

    //拿到超市报表
    @GetMapping("/excel/SupermarketExcelGet")
    public List<List<Object>> supermarketExcelGet() throws Exception {
        List<List<Object>> excelData = null;
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_supermarket.xlsx";
            excelData = excelReader.readFromExcel(excelFilePath);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return excelData;
    }

    //拿到污染表
    @GetMapping("/excel/ContaminationExcelGet")
    public List<List<Object>> contaminationExcelGet() throws Exception {
        List<List<Object>> excelData = null;
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_Con.xlsx";
            excelData = excelReader.readFromExcel(excelFilePath);
        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return excelData;
    }

}
