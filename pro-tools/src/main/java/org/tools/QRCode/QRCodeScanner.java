package org.tools.QRCode;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author eensh
 */
public class QRCodeScanner {

    /**
     * @param filePath 二维码图片的路径
     * @return 扫描结果，如果扫描成功就返回解码后的二维码文本，失败返回null
     */
    public static String scanQRCode(String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(bitmap);

            return result.getText();
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
       // String qrCodeFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productMake/0000018c-c8fd-c038-8cd5-26ee6af1f203.png"; // 生成的二维码文件路径
        String qrCodeFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productLogistic/0000018c-c8fc-257b-b15c-ae41f891562b.png";
        String decodedText = scanQRCode(qrCodeFilePath);
        if (decodedText != null) {
            System.out.println("扫描结果： " + decodedText);
        } else {
            System.out.println("未能扫描到二维码");
        }
    }
}