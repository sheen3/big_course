import org.junit.Test;
import org.junit.runner.RunWith;
import org.product.ProductApplication;
import org.product.serviceimpl.ExcelGetImpl;
import org.product.serviceimpl.LogisticMapperImpl;
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
public class TestExcelGetImpl {
    @Autowired
    private ExcelGetImpl excelGetImpl;

    @Test
    public void LogisticExcelGet() throws Exception {
        excelGetImpl.logisticExcelGet();
    }

    @Test
    public void ProductExcelGet() throws Exception {
        excelGetImpl.productExcelGet();
    }

    @Test
    public void SupermarketExcelGet() throws Exception {
        excelGetImpl.supermarketExcelGet();
    }

    @Test
    public void ContaminationExcelGet() throws Exception {
        excelGetImpl.contaminationExcelGet();
    }
}
