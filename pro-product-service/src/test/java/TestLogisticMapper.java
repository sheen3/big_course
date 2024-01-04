import org.database.mysql.domain.Logistic;
import org.database.mysql.domain.LogisticsSupermarketRef;
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
        Logistic logistic = new Logistic();
        //   logistic.setLogisticId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
        logistic.setLogisticCompanyId("哇哈哈");
        logistic.setLogisticBatchId("sf105153");
        logistic.setLogisticVehicleInfo("赣A10003");
        logistic.setLogisticTime(Timestamp.valueOf(LocalDateTime.now()));
        logistic.setLogisticDestinationSupermarket("江西盒马");
        if (logisticMapperImpl.insertLogistic(logistic)) {
            System.out.println("物流信息新增成功！");
        } else {
            System.out.println("物流信息新增失败");
        }
    }


    //查询物流信息
    @Test
    public void selectOneLogistic() throws Exception {
        Logistic logistic = new Logistic();
        logistic.setLogisticId("0000018c-d490-6ff6-b2e0-5000dd3b2844");


        if (logisticMapperImpl.selectOneLogistic(logistic) != null) {
            System.out.println("查找成功");
        } else {
            System.out.println("查找失败");
        }
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
        Logistic logistic = new Logistic();
        logistic.setLogisticId("1742846218133835777");

        if (logisticMapperImpl.deleteLogistic(logistic)) {
            System.out.println("物流信息删除成功！");
        } else {
            System.out.println("物流信息删除失败！");
        }
    }

    //更新物流信息
    @Test
    public void updateLogistic() throws Exception {
        Logistic logistic = new Logistic();
        logistic.setLogisticId("0000018c-cae2-955e-910c-49237ba0738a");
        logistic.setLogisticBatchId("coco00234");
        if (logisticMapperImpl.updateLogistic(logistic)) {
            System.out.println("物流状态已更新！");
        } else {
            System.out.println("物流状态更新失败！");
        }

    }

    //产品与物流的关联
    @Test
    public void sendSupermarket() throws Exception {
        LogisticsSupermarketRef logisticsSupermarketRef = new LogisticsSupermarketRef();
        logisticsSupermarketRef.setLogisticId("0000018c-c8fc-257b-b15c-ae41f891562b");
        logisticsSupermarketRef.setSupermarketId("0000018c-ca5e-9f76-8ce8-d49e76be216d");

        if (logisticMapperImpl.sendSupermarket(logisticsSupermarketRef) != null) {
            System.out.println("物流与超市订单建立成功！");
        } else {
            System.out.println("物流与超市订单建立失败！");
        }

    }

    @Test
    public void LogisticExcel() {
        Logistic logistic = new Logistic();
        logistic.setLogisticId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
        logistic.setLogisticCompanyId("宝洁10dsf24542");
        logistic.setLogisticBatchId("sf1001523534");
        logistic.setLogisticVehicleInfo("津A10434");
        logistic.setLogisticTime(Timestamp.valueOf(LocalDateTime.now()));
        logistic.setLogisticDestinationSupermarket("北京盒马");
        logisticMapperImpl.LogisticExcel(logistic);

    }

}
