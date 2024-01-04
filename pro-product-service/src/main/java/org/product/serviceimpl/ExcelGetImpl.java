package org.product.serviceimpl;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.database.mysql.BaseMysqlComp;
import org.database.mysql.domain.Logistic;
import org.database.mysql.mapper.LogisticMapper;
import org.database.mysql.mapper.ProductMapper;
import org.database.mysql.mapper.QrCodeMapper;
import org.database.mysql.mapper.SupermarketMapper;
import org.springframework.stereotype.Service;
import org.tools.Excel.ExcelReader;
import org.tools.Excel.LogExcelWriter;
import org.tools.log.LogComp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.tools.Excel.FileUtil.readLocalFile;
import static org.tools.QRCode.QRCodeScanner.scanQRCode;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@Service
@Getter
public class ExcelGetImpl {
    public List<List<Object>> logisticExcelGet() throws Exception {
        List<List<Object>> excelData = null;
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_Logistic.xlsx";
            excelData = excelReader.readFromExcel(excelFilePath);
            return excelData;


        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return excelData;
    }

    public List<List<Object>> supermarketExcelGet() throws Exception {
        List<List<Object>> excelData = null;
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_supermarket.xlsx";
            excelData = excelReader.readFromExcel(excelFilePath);
            return excelData;

        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return excelData;
    }

    public List<List<Object>> contaminationExcelGet() throws Exception {
        List<List<Object>> excelData = null;
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_Con.xlsx";
            excelData = excelReader.readFromExcel(excelFilePath);
            return excelData;

        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return excelData;
    }

    public List<List<Object>> productExcelGet() throws Exception {
        List<List<Object>> excelData = null;
        try {
            ExcelReader excelReader = new ExcelReader();
            String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_product.xlsx";
            excelData = excelReader.readFromExcel(excelFilePath);
            return excelData;

        } catch (Exception e) {
            System.out.println("操作失败");
        }
        return excelData;

    }


}

