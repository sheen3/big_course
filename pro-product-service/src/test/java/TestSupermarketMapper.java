import org.database.mysql.domain.LogisticsSupermarketRef;
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
        supermarket.setSupermarketId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
        supermarket.setSupermarketName("便利蜂");
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
        log.setSupermarketId("0000018c-ca5e-9f76-8ce8-d49e76be216d");
        log.setLogisticId("0000018c-c8fc-257b-b15c-ae41f891562b");
        supermarketMapperImpl.getProduct(log);
        System.out.println("商品入库成功！");


    }


}
