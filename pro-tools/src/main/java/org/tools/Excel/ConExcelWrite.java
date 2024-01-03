package org.tools.Excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
public class ConExcelWrite {

    public void writeToExcel(String folderName, String fileName, String[] content) {
        String excelFilePath = folderName + "/" + fileName + ".xlsx";
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Sheet1");

            // 创建标题行
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("污染记录编号");
            headerRow.createCell(1).setCellValue("产品编号");
            headerRow.createCell(2).setCellValue("污染描述");
            headerRow.createCell(3).setCellValue("污染状态");

            // 写入数据行
            int nextRowIndex = sheet.getLastRowNum() + 1;
            Row dataRow = sheet.createRow(nextRowIndex);
            for (int i = 0; i < content.length; i++) {
                dataRow.createCell(i).setCellValue(content[i]);
            }

            // 将工作簿写入Excel文件
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }

            System.out.println("污染表已成功录入.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        LogExcelWriter excelWriter = new LogExcelWriter();

        String folderName = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel";
        String fileName = "pro";
        String[] content = {"John", "30"};

        excelWriter.writeToExcel(folderName, fileName, content);
    }
}
