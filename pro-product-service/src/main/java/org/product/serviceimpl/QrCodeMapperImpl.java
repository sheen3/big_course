package org.product.serviceimpl;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.database.mysql.BaseMysqlComp;
import org.database.mysql.domain.Logistic;
import org.database.mysql.domain.Product;
import org.database.mysql.domain.ProductLogisticRef;
import org.database.mysql.domain.QrCode;
import org.database.mysql.entity.MysqlBuilder;
import org.database.mysql.mapper.ProductLogisticRefMapper;
import org.database.mysql.mapper.QrCodeMapper;
import org.springframework.stereotype.Service;
import org.tools.log.LogComp;
import org.tools.log.LogEnum;
import org.tools.log.LogType;

import java.io.File;
import java.util.List;

import static org.tools.QRCode.QRCodeScanner.scanQRCode;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */

@Service
@Getter
public class QrCodeMapperImpl {
    private final QrCodeMapper qrCodeMapper;
    private static final Logger log = LogComp.getLogger(QrCodeMapperImpl.class);
    private final BaseMysqlComp baseMysqlComp;
    private final ProductLogisticRefMapper productLogisticRefMapper;

    public QrCodeMapperImpl(QrCodeMapper qrCodeMapper, BaseMysqlComp baseMysqlComp, ProductLogisticRefMapper productLogisticRefMapper) {
        this.qrCodeMapper = qrCodeMapper;
        this.baseMysqlComp = baseMysqlComp;
        this.productLogisticRefMapper = productLogisticRefMapper;
    }


    /**
     * 删除本地文件二维码
     *
     * @param filePath
     */
    public Boolean deleteQrCodeFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("二维码文件已成功删除：" + filePath);
                return true;
            } else {
                System.out.println("无法删除二维码文件：" + filePath);
                return false;
            }
        } else {
            System.out.println("二维码文件不存在：" + filePath);
        }
        return null;
    }

    /**
     * 删除二维码
     *
     * @param qrCode
     * @throws Exception
     */
    public Boolean deleteQrCode(QrCode qrCode) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.QrCode);
            if (qrCode == null) {
                logMessage.build(LogEnum.QRCODE_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<QrCode> deleteQrCode = new MysqlBuilder<>(QrCode.class);
                deleteQrCode.setIn(qrCode);

                if (baseMysqlComp.selectOne(deleteQrCode) == null) {
                    logMessage.build(LogEnum.QRCODE_NO_EXISTS);
                    log.error(logMessage.log());
                } else {
                    baseMysqlComp.delete(deleteQrCode);
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to delete qrcode!", e);
        }
        return null;
    }


    /**
     * 查找二维码
     *
     * @param qrCode
     * @throws Exception
     */
    public QrCode selectOneQrCode(QrCode qrCode) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.QrCode);
            if (qrCode == null) {
                logMessage.build(LogEnum.QRCODE_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<QrCode> selectOneQrCode = new MysqlBuilder<>(QrCode.class);
                selectOneQrCode.setIn(qrCode);
                if (baseMysqlComp.selectOne(selectOneQrCode) == null) {
                    logMessage.build(LogEnum.QRCODE_EXISTS);
                    log.error(logMessage.log());

                } else {
                    return baseMysqlComp.selectOne(selectOneQrCode);
                }
            }

        } catch (Exception e) {
            log.error("Failed to select qrcode!", e);
        }
        return null;
    }

    public String sanProductQrCode(Product product) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.PRODUCT);
            if (product == null) {
                logMessage.build(LogEnum.PRODUCT_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<Product> sanQrCode = new MysqlBuilder<>(Product.class);
                sanQrCode.setIn(product);
                if (baseMysqlComp.selectOne(sanQrCode) == null) {
                    logMessage.build(LogEnum.PRODUCT_NO_EXISTS);
                    log.error(logMessage.log());

                } else {
                    String productQrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productMake";
                    String productQrCodeFileName = product.getProductId() + ".png";
                    String productQrCodeFilePath = productQrCodeFolderPath + "/" + productQrCodeFileName;
                    String productText = scanQRCode(productQrCodeFilePath);
                    if (productText != null) {
                        System.out.println("扫描结果： " + productText);
                        return productText;
                    } else {
                        System.out.println("未能扫描到二维码");
                        return "未能扫描到二维码";
                    }

                }
            }

        } catch (Exception e) {
            log.error("Failed to san Product!", e);
        }
        return null;
    }

    public String sanLogisticQrCode(Logistic logistic) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.LOGISTIC);
            if (logistic == null) {
                logMessage.build(LogEnum.LOGISTIC_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<Logistic> sanQrCode = new MysqlBuilder<>(Logistic.class);
                sanQrCode.setIn(logistic);
                if (baseMysqlComp.selectOne(sanQrCode) == null) {
                    logMessage.build(LogEnum.LOGISTIC_NO_EXISTS);
                    log.error(logMessage.log());

                } else {
                    String logisticQrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productLogistic";
                    String logisticQrCodeFileName = logistic.getLogisticId() + ".png";
                    String logisticQrCodeFilePath = logisticQrCodeFolderPath + "/" + logisticQrCodeFileName;
                    String logisticText = scanQRCode(logisticQrCodeFilePath);
                    if (logisticText != null) {
                        System.out.println("扫描结果： " + logisticText);
                        return logisticText;
                    } else {
                        System.out.println("未能扫描到二维码");
                        return "未能扫描到二维码";
                    }

                }
            }

        } catch (Exception e) {
            log.error("Failed to san Logistic!", e);
        }
        return null;

    }

    public String sanProAndLogQrCode(Product product) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.PRODUCT);
            if (product == null) {
                logMessage.build(LogEnum.PRODUCT_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<Product> sanQrCode = new MysqlBuilder<>(Product.class);
                sanQrCode.setIn(product);

                if (baseMysqlComp.selectOne(sanQrCode) == null) {
                    logMessage.build(LogEnum.PRODUCT_NO_EXISTS);
                    log.error(logMessage.log());

                } else {
                    ProductLogisticRef logi=new ProductLogisticRef();
                    logi.setProductId(product.getProductId());
                    MysqlBuilder<ProductLogisticRef> sanLog = new MysqlBuilder<>(ProductLogisticRef.class);
                    sanLog.setIn(logi);
                    logi=baseMysqlComp.selectOne(sanLog);

                    String logisticQrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productLogistic";
                    String logisticQrCodeFileName = logi.getLogisticId() + ".png";
                    String logisticQrCodeFilePath = logisticQrCodeFolderPath + "/" + logisticQrCodeFileName;
                    String logisticText = scanQRCode(logisticQrCodeFilePath);
                    if (logisticText != null) {
                        System.out.println("扫描结果： " + logisticText);
                        return logisticText;
                    } else {
                        System.out.println("未能扫描到二维码");
                        return "未能扫描到二维码";
                    }

                }
            }

        } catch (Exception e) {
            log.error("Failed to san Product!", e);
        }
        return null;
    }





    public List<QrCode> selectAllQrCode() throws Exception {
        MysqlBuilder<QrCode> selectAllQrCode = new MysqlBuilder<>(QrCode.class);
        return baseMysqlComp.selectList(selectAllQrCode);
    }

    /**
     * 产品信息和物流信息关联起来
     * 打包
     *
     * @param productLogisticRef
     * @throws Exception
     */
    public Boolean packProduct(ProductLogisticRef productLogisticRef) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.PRODUCT);
            LogComp.LogMessage logMessage1 = LogComp.buildData(LogType.LOGISTIC);

            if (productLogisticRef == null || (productLogisticRef.getProductId() == null && productLogisticRef.getLogisticId() == null)) {
                logMessage.build(LogEnum.PRODUCT_EMPTY);
                log.warn(logMessage.log());
            } else {

                MysqlBuilder<ProductLogisticRef> insertProductLogisticRef = new MysqlBuilder<>(ProductLogisticRef.class);
                insertProductLogisticRef.setIn(productLogisticRef);

                if (productLogisticRefMapper.selectById(productLogisticRef.getProductId()) != null) {
                    logMessage.build(LogEnum.PRODUCT_EXISTS);
                    log.error(logMessage.log());
                } else if (productLogisticRefMapper.selectById(productLogisticRef.getLogisticId()) != null) {
                    logMessage1.build(LogEnum.LOGISTIC_EXISTS);
                    log.error(logMessage.log());
                } else {
                    // 执行插入操作
                    baseMysqlComp.insert(insertProductLogisticRef);

                    //贴两张二维码
                    // 二维码文件夹路径
                    String logisticQrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productLogistic";
                    String logisticQrCodeFileName = productLogisticRef.getLogisticId() + ".png";
                    String logisticQrCodeFilePath = logisticQrCodeFolderPath + "/" + logisticQrCodeFileName;
                    String logisticText = scanQRCode(logisticQrCodeFilePath);
                    if (logisticText != null) {
                        System.out.println("扫描结果： " + logisticText);
                    } else {
                        System.out.println("未能扫描到二维码");
                    }
                    String productQrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productMake";
                    String productQrCodeFileName = productLogisticRef.getProductId() + ".png";
                    String productQrCodeFilePath = productQrCodeFolderPath + "/" + productQrCodeFileName;
                    String productText = scanQRCode(productQrCodeFilePath);
                    if (productText != null) {
                        System.out.println("扫描结果： " + productText);

                    } else {
                        System.out.println("未能扫描到二维码");

                    }

                    return true;

                }
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to insert productLogisticRef!", e);
        }
        return null;
    }


}
