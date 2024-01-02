import org.database.mysql.domain.Logistic;
import org.database.mysql.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.product.ProductApplication;
import org.product.serviceimpl.LogisticMapperImpl;
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
 * @CreateDate: 2024/1/2
 */

@SpringBootTest(classes = ProductApplication.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class TestLogisticMapper {

    @Autowired
    private LogisticMapperImpl logisticMapperImpl;
    //产生运输信息
    @Test
    public void insertLogistic() throws Exception {
        Logistic logistic=new Logistic();
        logistic.setLogisticId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
        logistic.setLogisticCompanyId("宝洁1001");
        logistic.setLogisticBatchId("sf100001");
        logistic.setLogisticVehicleInfo("津A10001");
        logistic.setLogisticTime(Timestamp.valueOf(LocalDateTime.now()));
        logistic.setLogisticDestinationSupermarket("天津盒马");

        logisticMapperImpl.insertLogistic(logistic);
        System.out.println("物流信息新增成功！");
    }


    //查询物流信息
    @Test
    public void selectOneLogistic() throws Exception {
        Logistic logistic=new Logistic();
        logistic.setLogisticId("0000018c-c7b8-e387-91b5-fbd4bcbe1939");
        System.out.println(logisticMapperImpl.selectOneLogistic(logistic));
        System.out.println("物流信息查找成功！");

        System.out.println("############################################################");
        System.out.println("以下是查询所有的产品得到信息:");
        List<Logistic> logisticsList = logisticMapperImpl.selectAllLogistic();
        for (Logistic p : logisticsList) {
            System.out.println(p);
        }

    }

    //删除物流信息
    @Test
    public void deleteLogistic() throws Exception {
        Logistic logistic=new Logistic();
        logistic.setLogisticId("0000018c-c818-c020-902d-2ca81afc58e3");
        logisticMapperImpl.deleteLogistic(logistic);
        System.out.println("物流信息删除成功！");
    }

    //更新物流信息
    @Test
    public void updateLogistic() throws Exception {
        Logistic logistic=new Logistic();
        logistic.setLogisticId("0000018c-c814-ce74-9cc4-6e067cb8643d");
        logistic.setLogisticBatchId("yt100001");
        logisticMapperImpl.updateLogistic(logistic);
        System.out.println("物流状态已更新！");

    }
}
