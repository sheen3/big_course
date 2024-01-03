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

import static org.tools.QRCode.QRCodeScanner.scanQRCode;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@Service
@Getter
public class ExcelGetImpl {

    public void LogisticExcelGet() {
        ExcelReader excelReader = new ExcelReader();
         String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_Logistic.xlsx";
        excelReader.readFromExcel(excelFilePath);
    }

    public void ProductExcelGet() {
        ExcelReader excelReader = new ExcelReader();
       String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_product.xlsx";
        excelReader.readFromExcel(excelFilePath);
    }
    public void SupermarketExcelGet() {
        ExcelReader excelReader = new ExcelReader();
         String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_supermarket.xlsx";

        excelReader.readFromExcel(excelFilePath);
    }

    public void ContaminationExcelGet() {
        ExcelReader excelReader = new ExcelReader();
        String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_Con.xlsx";
        excelReader.readFromExcel(excelFilePath);
    }

}
