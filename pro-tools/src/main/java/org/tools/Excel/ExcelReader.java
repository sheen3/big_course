package org.tools.Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */

public class ExcelReader {
    public void readFromExcel(String excelFilePath) {
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
            for (Row row : sheet) {
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == CellType.STRING) {
                        String value = cell.getStringCellValue();
                        System.out.print(value + "\t");
                    } else if (cellType == CellType.NUMERIC) {
                        double value = cell.getNumericCellValue();
                        System.out.print(value + "\t");
                    } // 其他类型的单元格可以根据需要进行处理

                    // 在这里处理每个单元格的数据
                }
                System.out.println(); // 换行
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExcelReader excelReader = new ExcelReader();

       // String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_Logistic.xlsx";
      //  String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_product.xlsx";
       //String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_supermarket.xlsx";
        String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel/pro_Con.xlsx";

        excelReader.readFromExcel(excelFilePath);
    }
}