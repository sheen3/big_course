package org.tools.QRCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/30
 */

//制作二维码
public class QRCodeGenerator {

    /**
     * @param qrCodeData（要编码的文本信息）
     * @param qrCodeFolderPath（保存的二维码的文件夹路径）
     * @param qrCodeFileName（保存的二维码的文件名）
     * @param size（二维码的尺寸）
     * 生成包含指定文本信息的二维码并保存在指定的文件夹路径下
     */
    public static void generateQRCode(String qrCodeData, String qrCodeFolderPath, String qrCodeFileName, int size) {
        try {
            // 创建了一个hints映射，用来设置二维码生成的一些选项，例如字符集、容错级别和边距
            // 创建了qrCodeWriter实例使用encode方法生成了一个BitMatrix对象，包含了二维码的矩阵数据
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // 容错级别
            hints.put(EncodeHintType.MARGIN, 1); // 边距

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, size, size, hints);

            // 创建了BufferedImage对象，用来存储要生成的二维码图像，然后通过获取Graphics2D对象并设置颜色
            // 绘制了一个空白的矩形图像再根据BitMatrix对象的数据，遍历矩阵中每个位置，如果该位置是黑色就在图像上绘制一个黑色的像素点
            BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();

            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, size, size);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            // 创建二维码文件夹（如果不存在）
            File qrCodeFolder = new File(qrCodeFolderPath);
            if (!qrCodeFolder.exists()) {
                qrCodeFolder.mkdirs();
            }

            // 拼接二维码文件的完整路径
            String qrCodeFilePath = qrCodeFolderPath + "/" + qrCodeFileName;

            // 将二维码保存到指定路径
            ImageIO.write(bufferedImage, "png", new File(qrCodeFilePath));

            System.out.println("二维码已成功生成并保存到：" + qrCodeFilePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String qrCodeData = "Hello Mob!"; // 二维码内容
        String qrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productMake"; // 二维码文件夹路径
        String qrCodeFileName = "qrcode.png"; // 二维码文件名
        int size = 250; // 二维码尺寸

        generateQRCode(qrCodeData, qrCodeFolderPath, qrCodeFileName, size);
    }
}