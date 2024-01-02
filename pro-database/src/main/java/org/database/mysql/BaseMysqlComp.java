package org.database.mysql;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Getter;
import org.database.mysql.domain.*;
import org.database.mysql.entity.MysqlBuilder;
import org.database.mysql.entity.MysqlOptType;
import org.database.mysql.entity.MysqlResultType;
import org.database.mysql.mapper.*;
import org.database.mysql.utl.MysqlCommonUtil;
import org.springframework.stereotype.Component;
import org.tools.exception.FormatException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 * <p>
 * 基于mysql数据库操作的组件，提供了对数据库的查询、插入、更新和删除操作
 * *
 */


@Component
@Getter
public class BaseMysqlComp {

    private final MysqlCommonUtil mysqlCommonUtil;
    private final PowerMapper powerMapper;
    private final RoleMapper roleMapper;
    private final RolePowerRefMapper rolePowerRefMapper;
    private final UserMapper userMapper;
    private final UserRoleRefMapper userRoleRefMapper;
    private final ProductMapper productMapper;
    private final LogisticMapper logisticMapper;
    private final QrCodeMapper qrCodeMapper;
    private final ContaminationRecordMapper contaminationRecordMapper;
    private final SupermarketMapper supermarketMapper;
    private final ProductStorageMapper productStorageMapper;
    private final LogisticsSupermarketRefMapper logisticsSupermarketRefMapper;
    private final SupermarketProductRefMapper supermarketProductRefMapper;
    private final ProductLogisticRefMapper productLogisticRefMapper;
    private final LogisticQrCodeRefMapper logisticQrCodeRefMapper;
    private final ProductQrCodeRefMapper productQrCodeRefMapper;

    public BaseMysqlComp(
            PowerMapper powerMapper, RoleMapper roleMapper,
            RolePowerRefMapper rolePowerRefMapper,
            UserMapper userMapper,
            UserRoleRefMapper userRoleRefMapper,
            ProductMapper productMapper,
            MysqlCommonUtil mysqlCommonUtil,
            LogisticMapper logisticMapper, QrCodeMapper qrCodeMapper, ContaminationRecordMapper contaminationRecordMapper, SupermarketMapper supermarketMapper, ProductStorageMapper productStorageMapper, LogisticsSupermarketRefMapper logisticsSupermarketRefMapper, SupermarketProductRefMapper supermarketProductRefMapper, ProductLogisticRefMapper productLogisticRefMapper, LogisticQrCodeRefMapper logisticQrCodeRefMapper, ProductQrCodeRefMapper productQrCodeRefMapper) {

        this.powerMapper = powerMapper;
        this.roleMapper = roleMapper;
        this.rolePowerRefMapper = rolePowerRefMapper;
        this.userMapper = userMapper;
        this.userRoleRefMapper = userRoleRefMapper;
        this.productMapper = productMapper;
        this.mysqlCommonUtil = mysqlCommonUtil;
        this.logisticMapper = logisticMapper;
        this.qrCodeMapper = qrCodeMapper;
        this.contaminationRecordMapper = contaminationRecordMapper;
        this.supermarketMapper = supermarketMapper;
        this.productStorageMapper = productStorageMapper;
        this.logisticsSupermarketRefMapper = logisticsSupermarketRefMapper;
        this.supermarketProductRefMapper = supermarketProductRefMapper;
        this.productLogisticRefMapper = productLogisticRefMapper;
        this.logisticQrCodeRefMapper = logisticQrCodeRefMapper;
        this.productQrCodeRefMapper = productQrCodeRefMapper;
    }


    //接收一个泛型参数T使用MysqlBuilder<T>对象来构建数据库
    public <T> List<T> selectList(MysqlBuilder<T> mysqlBuilder) throws FormatException, IllegalAccessException {
        mysqlBuilder.setOptType(MysqlOptType.SELECT);
        mysqlBuilder.setResultType(MysqlResultType.LIST);
        Object o = doOpt(mysqlBuilder);
        if (o == null) {
            return null;
        }
        return (List<T>) o;
    }


    public <T> T selectOne(MysqlBuilder<T> mysqlBuilder) throws FormatException, IllegalAccessException {
        mysqlBuilder.setOptType(MysqlOptType.SELECT);
        mysqlBuilder.setResultType(MysqlResultType.ONE);
        Object o = doOpt(mysqlBuilder);
        if (o == null) {
            return null;
        }
        return (T) o;
    }

    public <T> Integer delete(MysqlBuilder<T> mysqlBuilder) throws FormatException, IllegalAccessException {
        mysqlBuilder.setOptType(MysqlOptType.DELETE);
        mysqlBuilder.setResultType(MysqlResultType.NULL);
        Object o = doOpt(mysqlBuilder);
        if (o == null) {
            return -1;
        }
        return (Integer) o;
    }

    public <T> Integer update(MysqlBuilder<T> mysqlBuilder) throws FormatException, IllegalAccessException {
        mysqlBuilder.setOptType(MysqlOptType.UPDATE);
        mysqlBuilder.setResultType(MysqlResultType.NULL);
        Object o = doOpt(mysqlBuilder);
        if (o == null) {
            return -1;
        }
        return (Integer) o;
    }

    public <T> Integer insert(MysqlBuilder<T> mysqlBuilder) throws FormatException, IllegalAccessException {
        mysqlBuilder.setOptType(MysqlOptType.INSERT);
        mysqlBuilder.setResultType(MysqlResultType.NULL);
        Object o = doOpt(mysqlBuilder);
        if (o == null) {
            return -1;
        }
        return (Integer) o;
    }

    //根据传过来的MysqlBuilder对象的操作类型，使用相应的Mapper对象执行具体的数据库操作

    public <T> Object doOpt(MysqlBuilder<T> mysqlBuilder) throws FormatException, IllegalAccessException {
        BaseMapper<T> baseMapper = getMapperByClass(mysqlBuilder.getClz());
        switch (mysqlBuilder.getOptType()) {
            case DELETE: {
                QueryWrapper<T> queryWrapper = new QueryWrapper<>();
                buildQueryWrapperData(queryWrapper, mysqlBuilder.getClz(), mysqlBuilder.getIn(), mysqlBuilder.getNoEqual());
                return baseMapper.delete(queryWrapper);
            }
            case SELECT: {
                QueryWrapper<T> queryWrapper = new QueryWrapper<>();
                buildQueryWrapperData(queryWrapper, mysqlBuilder.getClz(), mysqlBuilder.getIn(), mysqlBuilder.getNoEqual());
                buildQueryWrapperOutData(queryWrapper, mysqlBuilder.getClz(), mysqlBuilder.getOut());
                if (mysqlBuilder.getResultType() == MysqlResultType.LIST) {
                    return baseMapper.selectList(queryWrapper);
                } else {
                    return baseMapper.selectOne(queryWrapper);
                }
            }
            case UPDATE: {
                UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
                buildUpdateWrapperData(updateWrapper, mysqlBuilder.getClz(), mysqlBuilder.getIn(), mysqlBuilder.getNoEqual());
                return baseMapper.update(mysqlBuilder.getUpdate(), updateWrapper);
            }

            case INSERT: {
                return baseMapper.insert(mysqlBuilder.getIn());
            }
            default:
                return null;
        }
    }

    //根据传入的对象和字段信息，构建了相应查询条件和更新条件

    private <T> void buildUpdateWrapperData(UpdateWrapper<T> updateWrapper, Class<T> clz, T in, T noEqual) throws IllegalAccessException, FormatException {
        Field[] fields = clz.getDeclaredFields();
        if (in != null) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object o = field.get(in);
                if (o != null) {
                    updateWrapper.eq(mysqlCommonUtil.javaFieldName2MysqlColumnsName(field.getName()), o);
                }
            }
        }
        if (noEqual != null) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object no = field.get(noEqual);
                if (no != null) {
                    updateWrapper.ne(mysqlCommonUtil.javaFieldName2MysqlColumnsName(field.getName()), no);
                }
            }
        }
    }

    private <T> void buildQueryWrapperData(QueryWrapper<T> queryWrapper, Class<T> clz, T in, T noEqual) throws IllegalAccessException, FormatException {
        Field[] fields = clz.getDeclaredFields();
        if (in != null) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object o = field.get(in);
                if (o != null) {
                    queryWrapper.eq(mysqlCommonUtil.javaFieldName2MysqlColumnsName(field.getName()), o);
                }
            }
        }
        if (noEqual != null) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object no = field.get(noEqual);
                if (no != null) {
                    queryWrapper.ne(mysqlCommonUtil.javaFieldName2MysqlColumnsName(field.getName()), no);
                }
            }
        }
    }

    private <T> void buildQueryWrapperOutData(QueryWrapper<T> queryWrapper, Class<T> clz, T out) throws IllegalAccessException, FormatException {
        if (out == null) {
            return;
        }
        Field[] fields = clz.getDeclaredFields();
        List<String> selectColumns = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(out);
            if (o != null) {
                selectColumns.add(mysqlCommonUtil.javaFieldName2MysqlColumnsName(field.getName()));
            }
        }
        queryWrapper.select(selectColumns.toArray(new String[0]));
    }

    //根据传入的类对象，选择相应的Mapper对象进行返回
    private BaseMapper getMapperByClass(Class<?> clz) {
        if (clz.equals(Power.class)) {
            return powerMapper;
        } else if (clz.equals(Role.class)) {
            return roleMapper;
        } else if (clz.equals(RolePowerRef.class)) {
            return rolePowerRefMapper;
        } else if (clz.equals(User.class)) {
            return userMapper;
        } else if (clz.equals(UserRoleRef.class)) {
            return userRoleRefMapper;
        } else if (clz.equals(Product.class)) {
            return productMapper;
        } else if (clz.equals(Logistic.class)) {
            return logisticMapper;
        } else if (clz.equals(Supermarket.class)) {
            return supermarketMapper;
        } else if (clz.equals(QrCode.class)) {
            return qrCodeMapper;
        } else if (clz.equals(ContaminationRecord.class)) {
            return contaminationRecordMapper;
        } else if (clz.equals(LogisticQrCodeRef.class)) {
            return logisticQrCodeRefMapper;
        } else if (clz.equals(LogisticsSupermarketRefMapper.class)) {
            return logisticsSupermarketRefMapper;
        } else if (clz.equals(ProductLogisticRef.class)) {
            return productLogisticRefMapper;
        } else if (clz.equals(ProductQrCodeRef.class)) {
            return productQrCodeRefMapper;
        } else if (clz.equals(ProductStorage.class)) {
            return productStorageMapper;
        }else if (clz.equals(SupermarketProductRef.class)) {
            return supermarketProductRefMapper;
        }

        throw new IllegalStateException("Unexpected value: " + clz);
    }

    private <T> Wrapper<T> getWrapperByOptType(MysqlOptType optType) {
        switch (optType) {
            case DELETE:
            case SELECT:
                return new QueryWrapper<T>();
            case UPDATE:
                return new UpdateWrapper<T>();
            default:
                return null;
        }
    }
}
