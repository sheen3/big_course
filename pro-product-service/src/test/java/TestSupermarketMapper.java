import org.database.mysql.domain.Logistic;
import org.database.mysql.domain.LogisticsSupermarketRef;
import org.database.mysql.domain.Product;
import org.database.mysql.domain.Supermarket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.product.ProductApplication;
import org.product.serviceimpl.SupermarketMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tools.common.uuid.UuidGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/2
 */

@SpringBootTest(classes = ProductApplication.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class TestSupermarketMapper {
    @Autowired
    private SupermarketMapperImpl supermarketMapperImpl;

    //添加超市
    @Test
    public void insertSupermarket() throws Exception{
        Supermarket supermarket=new Supermarket();
     //   supermarket.setSupermarketId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
        supermarket.setSupermarketName("yoyo");
        supermarket.setSupermarketAddress("浙江");
        supermarket.setSupermarketContact("079500002");

        supermarketMapperImpl.insertSupermarket(supermarket);
        System.out.println("关联商店添加成功！");
    }


    @Test
    public void selectOneSupermarket() throws Exception {
        Supermarket supermarket=new Supermarket();
        supermarket.setSupermarketId("0000018c-ca52-3e64-8442-2de6991684ab");
        supermarket.setSupermarketName("盒马");
        supermarketMapperImpl.selectOneSupermarket(supermarket);
        System.out.println("超市查找成功！");

        System.out.println("############################################################");
        System.out.println("以下是查询所有的超市得到信息:");
        List<Supermarket> SupermarketList = supermarketMapperImpl.selectAllSupermarket();
        for (Supermarket p : SupermarketList) {
            System.out.println(p);
        }
    }

    @Test
    public void deleteSupermarket() throws Exception {
        Supermarket supermarket=new Supermarket();
        supermarket.setSupermarketId("0000018c-ca52-3e64-8442-2de6991684ab");
        supermarketMapperImpl.deleteSupermarket(supermarket);
        System.out.println("超市删除成功！");
    }

    @Test
    public void updateSupermarket() throws Exception{
        Supermarket supermarket=new Supermarket();
        supermarket.setSupermarketId("0000018c-ca5e-9f76-8ce8-d49e76be216d");
        supermarket.setSupermarketContact("10000001");
        supermarketMapperImpl.updateSupermarket(supermarket);
        System.out.println("超市信息更新成功！");


    }
    @Test
    public void getProduct() throws Exception{
        LogisticsSupermarketRef log=new LogisticsSupermarketRef();
        log.setSupermarketId("0000018c-cdf2-35fa-ae0d-db115e7515b0");
        log.setLogisticId("0000018c-cae2-955e-910c-49237ba0738a");
        supermarketMapperImpl.getProduct(log);
        System.out.println("商品入库成功！");


    }
    @Test
    public void SupermarketExcel() {
        Supermarket supermarket=new Supermarket();
      //  supermarket.setSupermarketId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
        supermarket.setSupermarketName("便利蜂");
        supermarket.setSupermarketAddress("浙江");
        supermarket.setSupermarketContact("079500002");
        supermarketMapperImpl.supermarketExcel(supermarket);

    }

    @Test
    public void supermarketGetCon() throws Exception {
        Product product=new Product();
       product.setProductId("0000018c-c8fd-c038-8cd5-26ee6af1f203");
        supermarketMapperImpl.supermarketGetCon(product);

    }


}
