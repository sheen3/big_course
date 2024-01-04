import org.database.mysql.domain.Logistic;
import org.database.mysql.domain.Product;
import org.database.mysql.domain.ProductLogisticRef;
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
        qrCode.setProductId("0000018c-c8fd-1388-83aa-aee760275df7");

        System.out.println(qrCodeMapperImpl.selectOneQrCode(qrCode));
        System.out.println("二维码信息查找成功！");

        System.out.println("############################################################");
        System.out.println("以下是查询所有的产品得到信息:");
        List<QrCode> qrcodeList = qrCodeMapperImpl.selectAllQrCode();
        for (QrCode p : qrcodeList) {
            System.out.println(p);
        }

    }

    @Test
    public void scanProductQrCode() throws Exception {
        Product product=new Product();
        product.setProductId("0000018c-d3e0-271f-8d8d-829a7a25ef2a");
       qrCodeMapperImpl.sanProductQrCode(product);
        System.out.println("二维码信息查找成功！");


    }
    @Test
    public void scanLogisticQrCode() throws Exception {
        Logistic logistic =new Logistic();
        logistic.setLogisticId("0000018c-c8fc-257b-b15c-ae41f891562b");
        qrCodeMapperImpl.sanLogisticQrCode(logistic);
        System.out.println("二维码信息查找成功！");


    }
    //打包张贴两张二维码
    @Test
    public void packProduct() throws Exception{
        ProductLogisticRef productLogisticRef=new ProductLogisticRef();
        productLogisticRef.setProductId("0000018c-c8fd-c038-8cd5-26ee6af1f203");
        productLogisticRef.setLogisticId("0000018c-c913-b31d-87ab-5eb8424f3019");
        qrCodeMapperImpl.packProduct(productLogisticRef);
    }

}

