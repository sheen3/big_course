import org.database.mysql.domain.Power;
import org.database.mysql.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.product.ProductApplication;
import org.product.serviceimpl.ProductMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tools.common.uuid.UuidGenerator;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */

@SpringBootTest(classes = ProductApplication.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class TestProductMapper {
    @Autowired
    private ProductMapperImpl productMapperImpl;


    //生产产品
    @Test
    public void insertProduct() throws Exception {
        Product product=new Product();
        product.setProductId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
        product.setProductName("洗发水");
        LocalDateTime now=LocalDateTime.now();
        product.setProductDate(Date.valueOf(now.toLocalDate()));
        LocalDateTime expirationDate=now.plusYears(3);//当前日期加上三年
        product.setProductExpirationDate(Date.valueOf(expirationDate.toLocalDate()));
        product.setProductEnterpriseId("1000034");//企业id
        product.setProductProductionId("200053");//生产id
        product.setProductionPlace("南昌");

        productMapperImpl.insertProduct(product);
        System.out.println("产品生产成功！");
    }

    //查询产品信息
    @Test
    public void selectOneProduct() throws Exception {
        Product product=new Product();
        product.setProductId("0000018c-c44d-857f-a546-51cc6b627d71");
        product.setProductName("洗衣液");
        product.setProductionPlace("天津");
        System.out.println(productMapperImpl.selectOneProduct(product));
        System.out.println("产品查找成功！");

        System.out.println("############################################################");
        System.out.println("以下是查询所有的产品得到信息:");
        List<Product> productList = productMapperImpl.selectAllProduct();
        for (Product p : productList) {
            System.out.println(p);
        }

    }

    //销毁产品
    @Test
    public void deleteProduct() throws Exception {
        Product product=new Product();
        product.setProductId("0000018c-c8c8-cc0c-b9ad-2bfe8154da34");
        productMapperImpl.deleteProduct(product);
        System.out.println("产品销毁成功！");
    }

    //更新产品内容
    @Test
    public void updateProduct() throws Exception {
        Product product=new Product();
        product.setProductId("0000018c-c8fd-c038-8cd5-26ee6af1f203");
        product.setProductEnterpriseId("16234");
        product.setProductionPlace("福建");
        productMapperImpl.updateProduct(product);
        System.out.println("产品状态已更新！");

    }

}
