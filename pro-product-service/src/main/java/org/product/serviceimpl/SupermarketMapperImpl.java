package org.product.serviceimpl;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.database.mysql.BaseMysqlComp;
import org.database.mysql.domain.*;
import org.database.mysql.entity.MysqlBuilder;
import org.database.mysql.mapper.LogisticMapper;
import org.database.mysql.mapper.SupermarketMapper;
import org.springframework.stereotype.Service;
import org.tools.Excel.proExcelWriter;
import org.tools.Excel.supExcelWriter;
import org.tools.log.LogComp;
import org.tools.log.LogEnum;
import org.tools.log.LogType;

import java.util.List;

import static org.tools.QRCode.QRCodeScanner.scanQRCode;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/2
 */
@Service
@Getter
public class SupermarketMapperImpl {
    private final SupermarketMapper supermarketMapper;
    private final LogisticMapper logisticMapper;
    private static final Logger log = LogComp.getLogger(SupermarketMapperImpl.class);
    private final BaseMysqlComp baseMysqlComp;

    public SupermarketMapperImpl(SupermarketMapper supermarketMapper, LogisticMapper logisticMapper, BaseMysqlComp baseMysqlComp) {
        this.supermarketMapper = supermarketMapper;
        this.logisticMapper = logisticMapper;
        this.baseMysqlComp = baseMysqlComp;
    }

    /**
     * 添加关联商店
     *
     * @param supermarket
     * @throws Exception
     */
    public void insertSupermarket(Supermarket supermarket) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.SUPERMARKET);

            if (supermarket == null || supermarket.getSupermarketId() == null) {
                logMessage.build(LogEnum.SUPERMARKET_EMPTY);
                log.warn(logMessage.log());
            } else {

                MysqlBuilder<Supermarket> insertSupermarket = new MysqlBuilder<>(Supermarket.class);
                insertSupermarket.setIn(supermarket);

                if (supermarketMapper.selectById(supermarket.getSupermarketId()) != null) {
                    logMessage.build(LogEnum.SUPERMARKET_EXISTS);
                    log.error(logMessage.log());
                } else {
                    // 执行插入操作
                    baseMysqlComp.insert(insertSupermarket);
                    System.out.println("超市信息已添加");

                    supExcelWriter excelWriter = new supExcelWriter();
                    String folderName = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel";
                    String fileName = "pro_supermarket";
                    String[] content = {supermarket.getSupermarketId(),
                            supermarket.getSupermarketName(),
                            supermarket.getSupermarketAddress(),
                            supermarket.getSupermarketContact(),
                    };
                    excelWriter.writeToExcel(folderName, fileName, content);
                }
            }
        } catch (Exception e) {
            log.error("Failed to insert supermarketMapper!", e);

        }

    }

    /**
     * 查找某一门店
     *
     * @param supermarket
     * @throws Exception
     */
    public void selectOneSupermarket(Supermarket supermarket) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.SUPERMARKET);

            if (supermarket == null || supermarket.getSupermarketId() == null) {
                logMessage.build(LogEnum.SUPERMARKET_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<Supermarket> selectOneSupermarket = new MysqlBuilder<>(Supermarket.class);
                selectOneSupermarket.setIn(supermarket);
                Supermarket selectedSupermarket = baseMysqlComp.selectOne(selectOneSupermarket);

                if (selectedSupermarket == null) {
                    logMessage.build(LogEnum.SUPERMARKET_NO_EXISTS);
                    log.error(logMessage.log());
                }
            }

        } catch (Exception e) {
            log.error("Failed to select supermarket!", e);
        }
    }


    /**
     * 查找所有关联门店
     *
     * @return
     * @throws Exception
     */
    public List<Supermarket> selectAllSupermarket() throws Exception {
        MysqlBuilder<Supermarket> selectAllSupermarket = new MysqlBuilder<>(Supermarket.class);
        return baseMysqlComp.selectList(selectAllSupermarket);
    }


    /**
     * 删除线下门店
     *
     * @param supermarket
     * @throws Exception
     */
    public void deleteSupermarket(Supermarket supermarket) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.SUPERMARKET);

            if (supermarket == null || supermarket.getSupermarketId() == null) {
                logMessage.build(LogEnum.SUPERMARKET_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<Supermarket> deleteSupermarket = new MysqlBuilder<>(Supermarket.class);
                deleteSupermarket.setIn(supermarket);
                if (baseMysqlComp.selectOne(deleteSupermarket) == null) {
                    logMessage.build(LogEnum.SUPERMARKET_NO_EXISTS);
                    log.error(logMessage.log());
                } else {
                    baseMysqlComp.delete(deleteSupermarket);

                }
            }
        } catch (Exception e) {
            log.error("Failed to delete Supermarket!", e);
        }
    }


    /**
     * 更新超市信息
     *
     * @param supermarket
     * @throws Exception
     */
    public void updateSupermarket(Supermarket supermarket) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.SUPERMARKET);

            if (supermarket == null || supermarket.getSupermarketId() == null) {
                logMessage.build(LogEnum.SUPERMARKET_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<Supermarket> updateSupermarket = new MysqlBuilder<>(Supermarket.class);
                Supermarket supermarket1 = supermarketMapper.selectById(supermarket.getSupermarketId());
                updateSupermarket.setIn(supermarket1);
                updateSupermarket.setUpdate(supermarket);
                if (supermarketMapper.selectById(supermarket.getSupermarketId()) == null) {
                    logMessage.build(LogEnum.SUPERMARKET_NO_EXISTS);
                    log.error(logMessage.log());
                } else {
                    baseMysqlComp.update(updateSupermarket);
                }
            }
        } catch (Exception e) {
            log.error("Failed to update Supermarket!", e);
        }
    }

    /**
     * 超市根据超市和物流单子拿到产品
     * 先判断单子对不对得上
     *
     * @param logisticsSupermarketRef
     * @throws Exception
     */
    public void getProduct(LogisticsSupermarketRef logisticsSupermarketRef) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.LOGISTIC);
            LogComp.LogMessage logMessage1 = LogComp.buildData(LogType.SUPERMARKET);

            if (logisticMapper.selectById(logisticsSupermarketRef.getLogisticId()) == null) {
                logMessage.build(LogEnum.LOGISTIC_NO_EXISTS);
                log.warn(logMessage.log());
            } else if (supermarketMapper.selectById(logisticsSupermarketRef.getSupermarketId()) == null) {
                logMessage1.build(LogEnum.SUPERMARKET_NO_EXISTS);
                log.warn(logMessage.log());
            } else {
                //由物流和超市订单找到商品和物流的单子然后进行扫码

                ProductLogisticRef log = new ProductLogisticRef();
                log.setLogisticId(logisticsSupermarketRef.getLogisticId());
                MysqlBuilder<ProductLogisticRef> getLog = new MysqlBuilder<>(ProductLogisticRef.class);
                getLog.setIn(log);
                log = baseMysqlComp.selectOne(getLog);

                String logisticQrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productLogistic";
                String logisticQrCodeFileName = log.getLogisticId() + ".png";
                String logisticQrCodeFilePath = logisticQrCodeFolderPath + "/" + logisticQrCodeFileName;
                String logisticText = scanQRCode(logisticQrCodeFilePath);
                if (logisticText != null) {
                    System.out.println("物流码扫描结果： " + logisticText);
                } else {
                    System.out.println("未能扫描到二维码");
                }
                String productQrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productMake";
                String productQrCodeFileName = log.getProductId() + ".png";
                String productQrCodeFilePath = productQrCodeFolderPath + "/" + productQrCodeFileName;
                String productText = scanQRCode(productQrCodeFilePath);
                if (productText != null) {
                    System.out.println("生产码扫描结果： " + productText);
                } else {
                    System.out.println("未能扫描到二维码");
                }
                if (logisticText != null && productText != null) {
                    ProductStorage proSto = new ProductStorage();
                    proSto.setSupermarketId(logisticsSupermarketRef.getSupermarketId());
                    proSto.setProductId(log.getProductId());
                    MysqlBuilder<ProductStorage> get = new MysqlBuilder<>(ProductStorage.class);
                    get.setIn(proSto);
                    //入库
                    baseMysqlComp.insert(get);

                }

            }
        } catch (Exception e) {
            log.error("Failed to getProduct!", e);
        }
    }

    /**
     * 接到污染通知
     * 下架商品
     *
     * @param productStorage
     * @throws Exception
     */
    public void outProduct(ProductStorage productStorage) throws Exception {
        try {
            MysqlBuilder<ProductStorage> outProduct = new MysqlBuilder<>(ProductStorage.class);
            outProduct.setIn(productStorage);
            baseMysqlComp.delete(outProduct);

        } catch (Exception e) {
            log.error("Failed to outProduct!", e);

        }
    }

    public void supermarketExcel(Supermarket supermarket) {
        supExcelWriter excelWriter = new supExcelWriter();
        String folderName = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel";
        String fileName = "pro_supermarket";
        String[] content = {supermarket.getSupermarketId(),
                supermarket.getSupermarketName(),
                supermarket.getSupermarketAddress(),
                supermarket.getSupermarketContact(),
        };
        excelWriter.writeToExcel(folderName, fileName, content);
    }
}