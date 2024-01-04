package org.tools.Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */

public class ExcelReader {
    public List<List<Object>> readFromExcel(String excelFilePath) {
        List<List<Object>> data=new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
            for (Row row : sheet) {
                List<Object> rowData=new ArrayList<>();
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == CellType.STRING) {
                        String value = cell.getStringCellValue();
                        System.out.print(value + "\t");
                    } else if (cellType == CellType.NUMERIC) {
                        double value = cell.getNumericCellValue();
                        rowData.add(value);
                        System.out.print(value + "\t");
                    }

                }
                System.out.println(); // 换行
                data.add(rowData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
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