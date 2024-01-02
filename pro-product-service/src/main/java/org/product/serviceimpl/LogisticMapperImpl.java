package org.product.serviceimpl;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.database.mysql.BaseMysqlComp;
import org.database.mysql.domain.Logistic;
import org.database.mysql.domain.Product;
import org.database.mysql.domain.QrCode;
import org.database.mysql.entity.MysqlBuilder;
import org.database.mysql.mapper.ProductMapper;
import org.database.mysql.mapper.LogisticMapper;
import org.database.mysql.mapper.QrCodeMapper;
import org.springframework.stereotype.Service;
import org.tools.QRCode.QRCodeGenerator;
import org.tools.common.uuid.UuidGenerator;
import org.tools.log.LogComp;
import org.tools.log.LogEnum;
import org.tools.log.LogType;

import java.io.File;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2024/1/2
 */

@Service
@Getter
public class LogisticMapperImpl {
    private final LogisticMapper logisticMapper;
    private final ProductMapper productMapper;
    private static final Logger log = LogComp.getLogger(ProductMapperImpl.class);
    private final BaseMysqlComp baseMysqlComp;
    private final QrCodeMapper qrCodeMapper;
    private final QrCodeMapperImpl qrCodeMapperImpl;

    public LogisticMapperImpl(org.database.mysql.mapper.LogisticMapper logisticMapper, ProductMapper productMapper, BaseMysqlComp baseMysqlComp, QrCodeMapper qrCodeMapper, QrCodeMapperImpl qrCodeMapperImpl) {
        this.logisticMapper = logisticMapper;
        this.productMapper = productMapper;
        this.baseMysqlComp = baseMysqlComp;
        this.qrCodeMapper = qrCodeMapper;
        this.qrCodeMapperImpl = qrCodeMapperImpl;
    }

    /**
     * 增加物流信息
     *
     * @param logistic
     * @throws Exception
     */
    public void insertLogistic(Logistic logistic) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.LOGISTIC);

            if (logistic == null || logistic.getLogisticId() == null) {
                logMessage.build(LogEnum.LOGISTIC_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<Logistic> insertLogistic = new MysqlBuilder<>(Logistic.class);
                insertLogistic.setIn(logistic);

                if (logisticMapper.selectById(logistic.getLogisticId()) != null) {
                    logMessage.build(LogEnum.LOGISTIC_EXISTS);
                    log.error(logMessage.log());
                } else {
                    // 执行插入操作
                    baseMysqlComp.insert(insertLogistic);
                    //生成二维码
                    String qrCodeData = "物流编号:" + logistic.getLogisticId() + '\n' +
                            "物流企业编号:" + logistic.getLogisticCompanyId() + '\n' +
                            "物流批次编号:" + logistic.getLogisticBatchId() + '\n' +
                            "物流车辆信息:" + logistic.getLogisticVehicleInfo() + '\n' +
                            "物流时间:" + logistic.getLogisticVehicleInfo() + '\n' +
                            "目的地超市信息:" + logistic.getLogisticDestinationSupermarket();
                    // 二维码文件夹路径
                    String qrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productLogistic";
                    int qrCodeSize = 250;
                    //创建产品二维码文件夹（如果不存在）
                    File qrCodeFolder = new File(qrCodeFolderPath);
                    if (!qrCodeFolder.exists()) {
                        qrCodeFolder.mkdir();
                    }
                    //生成二维码文件名称
                    String qrCodeFileName = logistic.getLogisticId() + ".png";

                    // 生成二维码并保存
                    QRCodeGenerator.generateQRCode(qrCodeData, qrCodeFolderPath, qrCodeFileName, qrCodeSize);
                    System.out.println("二维码已生成并保存在:" + qrCodeFolderPath + "/" + qrCodeFileName);

                    //将二维码信息插入QrCodeMapper表里
                    QrCode qrcode = new QrCode();
                    qrcode.setQrCodeId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
                    qrcode.setLogisticId(logistic.getLogisticId());
                    qrcode.setQrCodeContent("物流编号:" + logistic.getLogisticId() + '\n' +
                            "物流企业编号:" + logistic.getLogisticCompanyId() + '\n' +
                            "物流批次编号:" + logistic.getLogisticBatchId() + '\n' +
                            "物流车辆信息:" + logistic.getLogisticVehicleInfo() + '\n' +
                            "物流时间:" + logistic.getLogisticVehicleInfo() + '\n' +
                            "目的地超市信息:" + logistic.getLogisticDestinationSupermarket());

                    qrCodeMapper.insert(qrcode);
                    System.out.println("二维码信息已添加");
                }

            }
        } catch (Exception e) {
            log.error("Failed to insert product!", e);

        }
    }

    /**
     * 查询物流信息
     *
     * @param logistic
     * @return
     * @throws Exception
     */
    public Logistic selectOneLogistic(Logistic logistic) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.LOGISTIC);

            if (logistic == null || logistic.getLogisticId() == null) {
                logMessage.build(LogEnum.LOGISTIC_EMPTY);
                log.warn(logMessage.log());

            } else {
                MysqlBuilder<Logistic> selectOneLogist = new MysqlBuilder<>(Logistic.class);
                selectOneLogist.setIn(logistic);
                if (baseMysqlComp.selectOne(selectOneLogist) == null) {
                    logMessage.build(LogEnum.LOGISTIC_EXISTS);
                    log.error(logMessage.log());

                } else {
                    return baseMysqlComp.selectOne(selectOneLogist);
                }
            }
        } catch (Exception e) {
            log.error("Failed to select logistic!", e);
        }
        return null;
    }

    /**
     * 查询所有物流信息
     *
     * @return
     * @throws Exception
     */
    public List<Logistic> selectAllLogistic() throws Exception {
        MysqlBuilder<Logistic> selectAllPLogistic = new MysqlBuilder<>(Logistic.class);
        return baseMysqlComp.selectList(selectAllPLogistic);
    }

    /**
     * 删除物流信息
     *
     * @param logistic
     * @throws Exception
     */
    public void deleteLogistic(Logistic logistic) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.LOGISTIC);
            if (logistic == null || logistic.getLogisticId() == null) {
                logMessage.build(LogEnum.LOGISTIC_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<Logistic> deleteLogistic = new MysqlBuilder<>(Logistic.class);
                deleteLogistic.setIn(logistic);
                if (baseMysqlComp.selectOne(deleteLogistic) == null) {
                    logMessage.build(LogEnum.LOGISTIC_NO_EXISTS);
                    log.error(logMessage.log());
                } else {

                    //将二维码信息从文件里删除
                    String qrcodeFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productLogistic/" + logistic.getLogisticId() + ".png";
                    qrCodeMapperImpl.deleteQrCodeFile(qrcodeFilePath);
                    System.out.println("二维码已从本地文件中删除");

                    QrCode qrcode = new QrCode();
                    qrcode.setLogisticId(logistic.getLogisticId());
                    MysqlBuilder<QrCode> deleteQrCode = new MysqlBuilder<>(QrCode.class);
                    deleteQrCode.setIn(qrcode);
                    baseMysqlComp.delete(deleteQrCode);
                    System.out.println("二维码已从数据库中删除");
                    baseMysqlComp.delete(deleteLogistic);

                }
            }
        } catch (Exception e) {
            log.error("Failed to delete Product!", e);
        }
    }

    /**
     * 更新物流信息
     * @param logistic
     * @throws Exception
     */
    public void updateLogistic(Logistic logistic) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.LOGISTIC);
            if (logistic == null || logistic.getLogisticId() == null) {
                logMessage.build(LogEnum.LOGISTIC_EMPTY);
                log.warn(logMessage.log());
            } else {
                if (logisticMapper.selectById(logistic.getLogisticId()) == null) {
                    logMessage.build(LogEnum.LOGISTIC_NO_EXISTS);
                    log.error(logMessage.log());
                } else {
                    MysqlBuilder<Logistic> updateLogistic = new MysqlBuilder<>(Logistic.class);
                    Logistic logistic1 = logisticMapper.selectById(logistic.getLogisticId());
                    updateLogistic.setIn(logistic1);
                    updateLogistic.setUpdate(logistic);
                    baseMysqlComp.update(updateLogistic);

                    //将二维码信息从文件里删除
                    String qrcodeFilePath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productLogistic/" + logistic1.getLogisticId() + ".png";
                    qrCodeMapperImpl.deleteQrCodeFile(qrcodeFilePath);
                    System.out.println("二维码已从本地文件中删除");


                    Logistic logistic2 = logisticMapper.selectById(logistic.getLogisticId());
                    updateLogistic.setIn(logistic2);

                    //将二维码信息从数据库里更新
                    MysqlBuilder<QrCode> findQrCode = new MysqlBuilder<>(QrCode.class);
                    QrCode qrCode = new QrCode();
                    qrCode.setLogisticId(logistic2.getLogisticId());
                    qrCode.setQrCodeContent("物流编号:" + logistic.getLogisticId() + '\n' +
                            "物流企业编号:" + logistic.getLogisticCompanyId() + '\n' +
                            "物流批次编号:" + logistic.getLogisticBatchId() + '\n' +
                            "物流车辆信息:" + logistic.getLogisticVehicleInfo() + '\n' +
                            "物流时间:" + logistic.getLogisticVehicleInfo() + '\n' +
                            "目的地超市信息:" + logistic.getLogisticDestinationSupermarket());

                    MysqlBuilder<QrCode> updateQrCode = new MysqlBuilder<>(QrCode.class);
                    qrCode.setLogisticId(logistic1.getLogisticId());
                    QrCode qrCode1 = baseMysqlComp.selectOne(findQrCode.buildIn(qrCode));
                    updateQrCode.setIn(qrCode1);
                    updateQrCode.setUpdate(qrCode);
                    baseMysqlComp.update(updateQrCode);
                    System.out.println("二维码信息已更新数据库");



                    //将二维码信息插入QrCodeMapper表里
                    QrCode qrcode = new QrCode();
                    qrcode.setLogisticId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
                    qrcode.setLogisticId(logistic2.getLogisticId());
                    qrcode.setQrCodeContent("物流编号:" + logistic.getLogisticId() + '\n' +
                            "物流企业编号:" + logistic.getLogisticCompanyId() + '\n' +
                            "物流批次编号:" + logistic.getLogisticBatchId() + '\n' +
                            "物流车辆信息:" + logistic.getLogisticVehicleInfo() + '\n' +
                            "物流时间:" + logistic.getLogisticVehicleInfo() + '\n' +
                            "目的地超市信息:" + logistic.getLogisticDestinationSupermarket());

                    qrCodeMapper.insert(qrcode);
                    System.out.println("二维码信息已添加入数据库");


                    //生成二维码
                    String qrCodeData = "物流编号:" + logistic.getLogisticId() + '\n' +
                            "物流企业编号:" + logistic.getLogisticCompanyId() + '\n' +
                            "物流批次编号:" + logistic.getLogisticBatchId() + '\n' +
                            "物流车辆信息:" + logistic.getLogisticVehicleInfo() + '\n' +
                            "物流时间:" + logistic.getLogisticVehicleInfo() + '\n' +
                            "目的地超市信息:" + logistic.getLogisticDestinationSupermarket();
                    // 二维码文件夹路径
                    String qrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productLogistic";
                    int qrCodeSize = 250;
                    //创建产品二维码文件夹（如果不存在）
                    File qrCodeFolder = new File(qrCodeFolderPath);
                    if (!qrCodeFolder.exists()) {
                        qrCodeFolder.mkdir();
                    }
                    //生成二维码文件名称
                    String qrCodeFileName = logistic2.getLogisticId() + ".png";

                    // 生成二维码并保存
                    QRCodeGenerator.generateQRCode(qrCodeData, qrCodeFolderPath, qrCodeFileName, qrCodeSize);
                    System.out.println("二维码已生成并保存在:" + qrCodeFolderPath + "/" + qrCodeFileName);


                }
            }


        } catch (Exception e) {
            log.error("Failed to update product!", e);
        }
    }


}
