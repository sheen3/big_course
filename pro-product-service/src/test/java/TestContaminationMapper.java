import org.database.mysql.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.product.ProductApplication;
import org.product.serviceimpl.ContaminationMapperImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@SpringBootTest(classes = ProductApplication.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class TestContaminationMapper {
    @Autowired
    private ContaminationMapperImpl contaminationMapperImpl;

    @Test
    public void insertContamination() throws Exception {
        Product product = new Product();
        product.setProductId("0000018c-c8fd-1388-83aa-aee760275df7");
        product.setProductName("洗发水");
        String des = "荧光剂超标";
        short status = 2;
        if (contaminationMapperImpl.insertContamination(product, des, status)) {
            System.out.println("污染产品已通报");
        } else {
            System.out.println("污染产品通报失败");
        }
    }


}
