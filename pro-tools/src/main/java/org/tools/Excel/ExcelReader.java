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
    public static void main(String[] args) {
        String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/pro.xlsx";
        try {
            FileInputStream fis = new FileInputStream(new File(excelFilePath));
            Workbook workbook = new XSSFWorkbook(fis);

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

            workbook.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}