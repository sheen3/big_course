package org.tools.Excel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/4
 */

public class FileUtil {
    public static String readLocalFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public static void main(String[] args) {
        String filePath = "文件路径";
        String fileContent = readLocalFile(filePath);
        System.out.println(fileContent);
    }
}