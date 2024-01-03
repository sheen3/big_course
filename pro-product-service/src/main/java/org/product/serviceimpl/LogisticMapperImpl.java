package org.product.serviceimpl;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.database.mysql.BaseMysqlComp;
import org.database.mysql.domain.*;
import org.database.mysql.entity.MysqlBuilder;
import org.database.mysql.mapper.*;
import org.springframework.stereotype.Service;
import org.tools.Excel.ExcelReader;
import org.tools.Excel.LogExcelWriter;
import org.tools.QRCode.QRCodeGenerator;
import org.tools.common.uuid.UuidGenerator;
import org.tools.log.LogComp;
import org.tools.log.LogEnum;
import org.tools.log.LogType;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    private static final Logger log = LogComp.getLogger(LogisticMapperImpl.class);
    private final BaseMysqlComp baseMysqlComp;
    private final QrCodeMapper qrCodeMapper;
    private final SupermarketMapper supermarketMapper;
    private final QrCodeMapperImpl qrCodeMapperImpl;

    public LogisticMapperImpl(LogisticMapper logisticMapper, ProductMapper productMapper, BaseMysqlComp baseMysqlComp, QrCodeMapper qrCodeMapper, SupermarketMapper supermarketMapper, QrCodeMapperImpl qrCodeMapperImpl) {
        this.logisticMapper = logisticMapper;
        this.productMapper = productMapper;
        this.baseMysqlComp = baseMysqlComp;
        this.qrCodeMapper = qrCodeMapper;
        this.supermarketMapper = supermarketMapper;
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

            if (logistic == null ) {
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

                    //建立物流和二维码的关联
                    LogisticQrCodeRef logisticQrCodeRef = new LogisticQrCodeRef();
                    logisticQrCodeRef.setLogisticId(logistic.getLogisticId());
                    logisticQrCodeRef.setQrCodeId(qrcode.getQrCodeId());
                    MysqlBuilder<LogisticQrCodeRef> insertLogisticQrCodeRef = new MysqlBuilder<>(LogisticQrCodeRef.class);
                    insertLogisticQrCodeRef.setIn(logisticQrCodeRef);
                    baseMysqlComp.insert(insertLogisticQrCodeRef);

                    //插入报表中
                    LogExcelWriter excelWriter = new LogExcelWriter();
                    String folderName = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel";
                    String fileName = "pro_Logistic";
                    Timestamp timestamp = logistic.getLogisticTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedTime = dateFormat.format(timestamp);
                    String[] content = {logistic.getLogisticId(),
                            logistic.getLogisticCompanyId(),
                            logistic.getLogisticBatchId(),
                            logistic.getLogisticVehicleInfo(),
                            formattedTime,
                            logistic.getLogisticDestinationSupermarket()
                    };
                    excelWriter.writeToExcel(folderName, fileName, content);
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

            if (logistic == null ) {
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
            if (logistic == null ) {
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
     *
     * @param logistic
     * @throws Exception
     */
    public void updateLogistic(Logistic logistic) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.LOGISTIC);
            if (logistic == null ) {
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


                    //将二维码信息从数据库里更新
                    Logistic logistic2 = logisticMapper.selectById(logistic.getLogisticId());

                    QrCode qrCodeFlag = new QrCode();
                    qrCodeFlag.setLogisticId(logistic1.getLogisticId());
                    MysqlBuilder<QrCode> findQrCode = new MysqlBuilder<>(QrCode.class);
                    findQrCode.setIn(qrCodeFlag);
                    QrCode qrCode1 = baseMysqlComp.selectOne(findQrCode);

                    MysqlBuilder<QrCode> updateQrCode = new MysqlBuilder<>(QrCode.class);

                    QrCode qrCodeUpdate = new QrCode();
                    qrCodeUpdate.setLogisticId(logistic2.getLogisticId());
                    qrCodeUpdate.setQrCodeContent("物流编号:" + logistic1.getLogisticId() + '\n' +
                            "物流企业编号:" + logistic1.getLogisticCompanyId() + '\n' +
                            "物流批次编号:" + logistic1.getLogisticBatchId() + '\n' +
                            "物流车辆信息:" + logistic1.getLogisticVehicleInfo() + '\n' +
                            "物流时间:" + logistic1.getLogisticVehicleInfo() + '\n' +
                            "目的地超市信息:" + logistic1.getLogisticDestinationSupermarket());


                    updateQrCode.setIn(qrCode1);
                    updateQrCode.setUpdate(qrCodeUpdate);

                    baseMysqlComp.update(updateQrCode);
                    System.out.println("二维码信息已更新数据库");


                    //生成二维码
                    String qrCodeData = "物流编号:" + logistic1.getLogisticId() + '\n' +
                            "物流企业编号:" + logistic1.getLogisticCompanyId() + '\n' +
                            "物流批次编号:" + logistic1.getLogisticBatchId() + '\n' +
                            "物流车辆信息:" + logistic1.getLogisticVehicleInfo() + '\n' +
                            "物流时间:" + logistic1.getLogisticTime() + '\n' +
                            "目的地超市信息:" + logistic1.getLogisticDestinationSupermarket();
                    // 二维码文件夹路径
                    String qrCodeFolderPath = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/productLogistic";
                    int qrCodeSize = 250;
                    //创建产品二维码文件夹（如果不存在）
                    File qrCodeFolder = new File(qrCodeFolderPath);
                    if (!qrCodeFolder.exists()) {
                        qrCodeFolder.mkdir();
                    }
                    //生成二维码文件名称
                    String qrCodeFileName = logistic1.getLogisticId() + ".png";

                    // 生成二维码并保存
                    QRCodeGenerator.generateQRCode(qrCodeData, qrCodeFolderPath, qrCodeFileName, qrCodeSize);
                    System.out.println("二维码已生成并保存在:" + qrCodeFolderPath + "/" + qrCodeFileName);


                }
            }


        } catch (Exception e) {
            log.error("Failed to update product!", e);
        }
    }

    /**
     * 超市与物流的关联
     * 可以看做超市和物流的一个订单
     * 先运输出去产品然后超市再获取产品
     *
     * @param logisticsSupermarketRef
     * @return
     * @throws Exception
     */
    public Boolean sendSupermarket(LogisticsSupermarketRef logisticsSupermarketRef) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.LOGISTIC);
            LogComp.LogMessage logMessage1 = LogComp.buildData(LogType.SUPERMARKET);

            MysqlBuilder<LogisticsSupermarketRef> flag = new MysqlBuilder<>(LogisticsSupermarketRef.class);
            flag.setIn(logisticsSupermarketRef);

            if (logisticMapper.selectById(logisticsSupermarketRef.getLogisticId()) == null) {
                logMessage.build(LogEnum.LOGISTIC_NO_EXISTS);
                log.warn(logMessage.log());
            } else if (supermarketMapper.selectById(logisticsSupermarketRef.getSupermarketId()) == null) {
                logMessage1.build(LogEnum.SUPERMARKET_NO_EXISTS);
                log.warn(logMessage.log());
            } else {
                baseMysqlComp.insert(flag);
                return baseMysqlComp.selectOne(flag) != null;
            }
        } catch (Exception e) {
            log.error("Failed to sendSupermarket!", e);
        }
        return null;
    }

    public void LogisticExcel(Logistic logistic) {
        LogExcelWriter excelWriter = new LogExcelWriter();
        String folderName = "/Users/eensh/Desktop/softwareIntegratedCourseDesign/Excel";
        String fileName = "pro_Logistic";
        Timestamp timestamp = logistic.getLogisticTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = dateFormat.format(timestamp);
        String[] content = {logistic.getLogisticId(),
                logistic.getLogisticCompanyId(),
                logistic.getLogisticBatchId(),
                logistic.getLogisticVehicleInfo(),
                formattedTime,
                logistic.getLogisticDestinationSupermarket()
        };
        excelWriter.writeToExcel(folderName, fileName, content);
    }


}




