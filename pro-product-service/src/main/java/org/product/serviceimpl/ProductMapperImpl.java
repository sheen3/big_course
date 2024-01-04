package org.product.serviceimpl;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.database.mysql.BaseMysqlComp;
import org.database.mysql.domain.*;
import org.database.mysql.entity.MysqlBuilder;
import org.database.mysql.mapper.ProductMapper;
import org.database.mysql.mapper.QrCodeMapper;
import org.springframework.stereotype.Service;
import org.tools.Excel.LogExcelWriter;
import org.tools.Excel.proExcelWriter;
import org.tools.common.uuid.UuidGenerator;
import org.tools.log.LogComp;
import org.tools.log.LogEnum;
import org.tools.log.LogType;
import org.tools.QRCode.QRCodeGenerator;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/1
 */

@Service
@Getter
public class ProductMapperImpl {
    private final ProductMapper productMapper;
    private static final Logger log = LogComp.getLogger(ProductMapperImpl.class);
    private final BaseMysqlComp baseMysqlComp;
    private final QrCodeMapper qrCodeMapper;
    private final QrCodeMapperImpl qrCodeMapperImpl;

    public ProductMapperImpl(ProductMapper productMapper, BaseMysqlComp baseMysqlComp, QrCodeMapper qrCodeMapper, QrCodeMapperImpl qrCodeMapperImpl) {
        this.productMapper = productMapper;
        this.baseMysqlComp = baseMysqlComp;
        this.qrCodeMapper = qrCodeMapper;
        this.qrCodeMapperImpl = qrCodeMapperImpl;
    }

    /**
     * 增加产品
     * 参数校验：产品id不为空；
     * 产品id不能已存在；
     *
     * @param product
     * @throws Exception
     */
    public Boolean insertProduct(Product product) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.PRODUCT);

            if (product == null) {
                logMessage.build(LogEnum.PRODUCT_EMPTY);
                log.warn(logMessage.log());
            } else {
                product.setProductId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());

                MysqlBuilder<Product> insertProduct = new MysqlBuilder<>(Product.class);
                insertProduct.setIn(product);

                if (productMapper.selectById(product.getProductId()) != null) {
                    logMessage.build(LogEnum.PRODUCT_EXISTS);
                    log.error(logMessage.log());
                } else {
                    // 执行插入操作
                    baseMysqlComp.insert(insertProduct);
                    //生成二维码
                    String qrCodeData = "产品编号:" + product.getProductId() + '\n' +
                            "产品名:" + product.getProductName() + '\n' +
                            "生产日期:" + product.getProductDate() + '\n' +
                            "到期时间:" + product.getProductExpirationDate() + '\n' +
                            "企业编号:" + product.getProductEnterpriseId() + '\n' +
                            "生产编号:" + product.getProductProductionId() + '\n' +
                            "生产地址:" + product.getProductionPlace();
                    // 二维码文件夹路径
                    String qrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productMake";
                    int qrCodeSize = 250;
                    //创建产品二维码文件夹（如果不存在）
                    File qrCodeFolder = new File(qrCodeFolderPath);
                    if (!qrCodeFolder.exists()) {
                        qrCodeFolder.mkdir();
                    }
                    //生成二维码文件名称
                    String qrCodeFileName = product.getProductId() + ".png";

                    // 生成二维码并保存
                    QRCodeGenerator.generateQRCode(qrCodeData, qrCodeFolderPath, qrCodeFileName, qrCodeSize);
                    System.out.println("二维码已生成并保存在:" + qrCodeFolderPath + "/" + qrCodeFileName);

                    //将二维码信息插入QrCodeMapper表里
                    QrCode qrcode = new QrCode();
                    qrcode.setQrCodeId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
                    qrcode.setProductId(product.getProductId());
                    qrcode.setQrCodeContent("产品编号:" + product.getProductId() + '\n' +
                            "产品名:" + product.getProductName() + '\n' +
                            "生产日期:" + product.getProductDate() + '\n' +
                            "到期时间:" + product.getProductExpirationDate() + '\n' +
                            "企业编号:" + product.getProductEnterpriseId() + '\n' +
                            "生产编号:" + product.getProductProductionId() + '\n' +
                            "生产地址:" + product.getProductionPlace());

                    qrCodeMapper.insert(qrcode);
                    System.out.println("二维码信息已添加");

                    //建立产品和二维码的关联
                    ProductQrCodeRef productQrCodeRef = new ProductQrCodeRef();
                    productQrCodeRef.setProductId(product.getProductId());
                    productQrCodeRef.setQrCodeId(qrcode.getQrCodeId());
                    MysqlBuilder<ProductQrCodeRef> insertProductQrCodeRef = new MysqlBuilder<>(ProductQrCodeRef.class);
                    insertProductQrCodeRef.setIn(productQrCodeRef);
                    baseMysqlComp.insert(insertProductQrCodeRef);

                    proExcelWriter excelWriter = new proExcelWriter();
                    String folderName = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel";
                    String fileName = "pro_product";
                    String[] content = {product.getProductName(),
                            String.valueOf(product.getProductDate()),
                            String.valueOf(product.getProductExpirationDate()),
                            product.getProductEnterpriseId(),
                            product.getProductProductionId(),
                            product.getProductionPlace(),
                    };
                    excelWriter.writeToExcel(folderName, fileName, content);
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to insert product!", e);

        }
        return null;
    }


    /**
     * 查找产品信息
     * 参数校验：可通过产品批次和产品名或者产品生产地找到产品；
     *
     * @param product
     * @throws Exception
     */
    public Boolean selectOneProduct(Product product) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.PRODUCT);

            if (product == null) {
                logMessage.build(LogEnum.PRODUCT_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<Product> selectOneProduct = new MysqlBuilder<>(Product.class);
                selectOneProduct.setIn(product);
                if (baseMysqlComp.selectOne(selectOneProduct) == null) {
                    logMessage.build(LogEnum.PRODUCT_EXISTS);
                    log.error(logMessage.log());

                } else {
                    if (baseMysqlComp.selectOne(selectOneProduct) != null) {
                        return true;
                    }
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("Failed to select product!", e);
        }
        return null;
    }

    /**
     * 查询产品列表信息
     *
     * @return
     * @throws Exception
     */
    public List<Product> selectAllProduct() throws Exception {
        MysqlBuilder<Product> selectAllProduct = new MysqlBuilder<>(Product.class);
        return baseMysqlComp.selectList(selectAllProduct);
    }

    /**
     * 销毁产品
     *
     * @param product
     * @throws Exception
     */
    public Boolean deleteProduct(Product product) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.PRODUCT);

            if (product == null) {
                logMessage.build(LogEnum.PRODUCT_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<Product> deleteProduct = new MysqlBuilder<>(Product.class);
                deleteProduct.setIn(product);
                if (baseMysqlComp.selectOne(deleteProduct) == null) {
                    logMessage.build(LogEnum.PRODUCT_NO_EXISTS);
                    log.error(logMessage.log());
                } else {

                    //将二维码信息从文件里删除
                    String qrcodeFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productMake/" + product.getProductId() + ".png";
                    qrCodeMapperImpl.deleteQrCodeFile(qrcodeFilePath);
                    System.out.println("二维码已从本地文件中删除");

                    QrCode qrcode = new QrCode();
                    qrcode.setProductId(product.getProductId());
                    MysqlBuilder<QrCode> deleteQrCode = new MysqlBuilder<>(QrCode.class);
                    deleteQrCode.setIn(qrcode);
                    baseMysqlComp.delete(deleteQrCode);
                    System.out.println("二维码已从数据库中删除");
                    baseMysqlComp.delete(deleteProduct);
                    return true;

                }
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to delete Product!", e);
        }
        return null;
    }

    /**
     * 更新产品信息
     * 参数校验：用户id不为空；
     *
     * @param product
     * @throws Exception
     */
    public Boolean updateProduct(Product product) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.PRODUCT);

            if (product == null) {
                logMessage.build(LogEnum.PRODUCT_EMPTY);
                log.warn(logMessage.log());
            } else {
                if (productMapper.selectById(product.getProductId()) == null) {
                    logMessage.build(LogEnum.PRODUCT_NO_EXISTS);
                    log.error(logMessage.log());
                } else {
                    MysqlBuilder<Product> updateProduct = new MysqlBuilder<>(Product.class);
                    Product product1 = productMapper.selectById(product.getProductId());
                    updateProduct.setIn(product1);
                    updateProduct.setUpdate(product);
                    baseMysqlComp.update(updateProduct);


                    //将二维码信息从文件里删除
                    String qrcodeFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productMake/" + product1.getProductId() + ".png";
                    qrCodeMapperImpl.deleteQrCodeFile(qrcodeFilePath);
                    System.out.println("二维码已从本地文件中删除");


                    //将二维码信息从数据库里更新
                    Product product2 = productMapper.selectById(product.getProductId());

                    QrCode qrCodeFlag = new QrCode();
                    qrCodeFlag.setProductId(product2.getProductId());
                    MysqlBuilder<QrCode> findQrCode = new MysqlBuilder<>(QrCode.class);
                    findQrCode.setIn(qrCodeFlag);
                    QrCode qrCode1 = baseMysqlComp.selectOne(findQrCode);

                    MysqlBuilder<QrCode> updateQrCode = new MysqlBuilder<>(QrCode.class);

                    QrCode qrCodeUpdate = new QrCode();
                    qrCodeUpdate.setProductId(product2.getProductId());
                    qrCodeUpdate.setQrCodeContent("产品编号:" + product.getProductId() + '\n' +
                            "产品名:" + product1.getProductName() + '\n' +
                            "生产日期:" + product1.getProductDate() + '\n' +
                            "到期时间:" + product1.getProductExpirationDate() + '\n' +
                            "企业编号:" + product1.getProductEnterpriseId() + '\n' +
                            "生产编号:" + product1.getProductProductionId() + '\n' +
                            "生产地址:" + product1.getProductionPlace());
                    updateQrCode.setIn(qrCode1);
                    updateQrCode.setUpdate(qrCodeUpdate);

                    baseMysqlComp.update(updateQrCode);
                    System.out.println("二维码信息已更新数据库");


                    //生成二维码
                    String qrCodeData = "产品编号:" + product.getProductId() + '\n' +
                            "产品名:" + product1.getProductName() + '\n' +
                            "生产日期:" + product1.getProductDate() + '\n' +
                            "到期时间:" + product1.getProductExpirationDate() + '\n' +
                            "企业编号:" + product1.getProductEnterpriseId() + '\n' +
                            "生产编号:" + product1.getProductProductionId() + '\n' +
                            "生产地址:" + product1.getProductionPlace();
                    // 二维码文件夹路径
                    String qrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productMake";
                    int qrCodeSize = 250;
                    //创建产品二维码文件夹（如果不存在）
                    File qrCodeFolder = new File(qrCodeFolderPath);
                    if (!qrCodeFolder.exists()) {
                        qrCodeFolder.mkdir();
                    }
                    //生成二维码文件名称
                    String qrCodeFileName = product2.getProductId() + ".png";

                    // 生成二维码并保存
                    QRCodeGenerator.generateQRCode(qrCodeData, qrCodeFolderPath, qrCodeFileName, qrCodeSize);

                    return true;
                }
                return false;
            }


        } catch (Exception e) {
            log.error("Failed to update product!", e);
        }
        return null;
    }

    /**
     * product报表
     *
     * @param product
     */
    public void productExcel(Product product) {
        proExcelWriter excelWriter = new proExcelWriter();
        String folderName = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel";
        String fileName = "pro_product";
        String[] content = {
                product.getProductId(),
                product.getProductName(),
                String.valueOf(product.getProductDate()),
                String.valueOf(product.getProductExpirationDate()),
                product.getProductEnterpriseId(),
                product.getProductProductionId(),
                product.getProductionPlace(),
        };
        excelWriter.writeToExcel(folderName, fileName, content);
    }

}
