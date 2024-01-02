import org.database.mysql.domain.Product;
import org.database.mysql.domain.QrCode;
import org.database.mysql.mapper.QrCodeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.product.ProductApplication;
import org.product.serviceimpl.ProductMapperImpl;
import org.product.serviceimpl.QrCodeMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */

@SpringBootTest(classes = ProductApplication.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class TestQrCodeMapper {
    @Autowired
    private QrCodeMapperImpl qrCodeMapperImpl;


    //删除二维码
    @Test
    public void deleteQrCode() throws Exception {
        QrCode qrCode=new QrCode();
      //  qrCode.setQrCodeId(13);
        qrCode.setProductId("0000018c-c7d2-ad13-89e4-e695c7090849");
        qrCodeMapperImpl.deleteQrCode(qrCode);
        System.out.println("二维码删除成功！");
    }

    //查询二维码
    @Test
    public void selectOneQrCode() throws Exception {
        QrCode qrCode=new QrCode();
       // qrCode.setQrCodeId(4);
        qrCode.setProductId("0000018c-c555-f88a-a6e6-74bb8b199dfc");

        System.out.println(qrCodeMapperImpl.selectOneQrCode(qrCode));
        System.out.println("二维码信息查找成功！");

        System.out.println("############################################################");
        System.out.println("以下是查询所有的产品得到信息:");
        List<QrCode> qrcodeList = qrCodeMapperImpl.selectAllQrCode();
        for (QrCode p : qrcodeList) {
            System.out.println(p);
        }

    }

}

