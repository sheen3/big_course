package org.tools.Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */


public class ExcelWriter {
    public static void main(String[] args) {
        String excelFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/pro.xlsx";
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            Row headerRow = sheet.createRow(0);
            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("Name");
            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("Age");

            Row dataRow1 = sheet.createRow(1);
            Cell dataCell1 = dataRow1.createCell(0);
            dataCell1.setCellValue("John");
            Cell dataCell2 = dataRow1.createCell(1);
            dataCell2.setCellValue(30);

            Row dataRow2 = sheet.createRow(2);
            Cell dataCell3 = dataRow2.createCell(0);
            dataCell3.setCellValue("Jane");
            Cell dataCell4 = dataRow2.createCell(1);
            dataCell4.setCellValue(28);

            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }

            System.out.println("Excel file written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}