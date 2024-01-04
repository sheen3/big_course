package org.product.serviceimpl;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.database.mysql.BaseMysqlComp;
import org.database.mysql.domain.ContaminationRecord;
import org.database.mysql.domain.Product;
import org.database.mysql.domain.Supermarket;
import org.database.mysql.entity.MysqlBuilder;
import org.database.mysql.mapper.ContaminationRecordMapper;
import org.database.mysql.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.tools.Excel.ConExcelWrite;
import org.tools.Excel.supExcelWriter;
import org.tools.common.uuid.UuidGenerator;
import org.tools.log.LogComp;
import org.tools.log.LogEnum;
import org.tools.log.LogType;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/3
 */
@Service
@Getter
public class ContaminationMapperImpl {
    private final ProductMapper productMapper;
    private final BaseMysqlComp baseMysqlComp;
    private final ContaminationRecordMapper contaminationRecordMapper;
    private static final Logger log = LogComp.getLogger(ProductMapperImpl.class);

    public ContaminationMapperImpl(ProductMapper productMapper, BaseMysqlComp baseMysqlComp, ContaminationRecordMapper contaminationRecordMapper) {
        this.productMapper = productMapper;
        this.baseMysqlComp = baseMysqlComp;
        this.contaminationRecordMapper = contaminationRecordMapper;
    }

    /**
     * 新建污染表
     *
     * @param product
     * @param des
     * @param status
     * @throws Exception
     */
    public Boolean insertContamination(Product product, String des, short status) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.PRODUCT);

            if (product == null ) {
                logMessage.build(LogEnum.PRODUCT_EMPTY);
                log.warn(logMessage.log());
            } else {

                MysqlBuilder<Product> insertProduct = new MysqlBuilder<>(Product.class);
                insertProduct.setIn(product);

                if (productMapper.selectById(product.getProductId()) == null) {
                    logMessage.build(LogEnum.PRODUCT_NO_EXISTS);
                    log.error(logMessage.log());

                } else {
                    ContaminationRecord con = new ContaminationRecord();
                    con.setRecordId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
                    con.setProductId(product.getProductId());
                    con.setContaminationDescription(des);
                    con.setRecordsStatus(status);
                    MysqlBuilder<ContaminationRecord> insertCon = new MysqlBuilder<>(ContaminationRecord.class);
                    insertCon.setIn(con);
                    baseMysqlComp.insert(insertCon);
                    //污染表
                    ContaminationRecord contaminationRecord = new ContaminationRecord();
                    contaminationRecord = baseMysqlComp.selectOne(insertCon);
                    ConExcelWrite excelWriter = new ConExcelWrite();
                    String folderName = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel";
                    String fileName = "pro_Con";
                    String[] content = {contaminationRecord.getRecordId(),
                            contaminationRecord.getProductId(),
                            contaminationRecord.getContaminationDescription(),
                            String.valueOf(contaminationRecord.getRecordsStatus()),
                    };
                    excelWriter.writeToExcel(folderName, fileName, content);
                    return true;
                }
            }
        } catch (
                Exception e) {
            log.error("Failed to insert Contamination!", e);
        }

        return false;
    }

    /**
     * 污染表生成
     *
     * @param contaminationRecord
     */
    public void conExcel(ContaminationRecord contaminationRecord) {
        ConExcelWrite excelWriter = new ConExcelWrite();
        String folderName = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel";
        String fileName = "pro_Con";
        String[] content = {contaminationRecord.getRecordId(),
                contaminationRecord.getProductId(),
                contaminationRecord.getContaminationDescription(),
                String.valueOf(contaminationRecord.getRecordsStatus()),
        };
        excelWriter.writeToExcel(folderName, fileName, content);
    }

}
