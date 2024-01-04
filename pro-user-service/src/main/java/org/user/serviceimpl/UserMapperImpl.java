package org.user.serviceimpl;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.database.mysql.BaseMysqlComp;
import org.database.mysql.domain.Role;
import org.database.mysql.domain.User;
import org.database.mysql.domain.UserRoleRef;
import org.database.mysql.entity.MysqlBuilder;
import org.database.mysql.mapper.PowerMapper;
import org.database.mysql.mapper.RoleMapper;
import org.database.mysql.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tools.common.encrypt.PasswordEncrypt;
import org.tools.common.uuid.UuidGenerator;
import org.tools.log.LogComp;
import org.tools.log.LogEnum;
import org.tools.log.LogType;

import java.util.List;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */

@Service
@Getter
public class UserMapperImpl {
    private final RoleMapper roleMapper;
    private final PowerMapper powerMapper;
    private final UserMapper userMapper;
    private final RoleMapperImpl roleMapperImpl;


    private final BaseMysqlComp baseMysqlComp;
    private static final Logger log = LogComp.getLogger(UserMapperImpl.class);


    @Autowired
    public UserMapperImpl(RoleMapper roleMapper, PowerMapper powerMapper, UserMapper userMapper, RoleMapperImpl roleMapperImpl, BaseMysqlComp baseMysqlComp) {
        this.roleMapper = roleMapper;
        this.powerMapper = powerMapper;
        this.userMapper = userMapper;
        this.roleMapperImpl = roleMapperImpl;
        this.baseMysqlComp = baseMysqlComp;

    }

    /**
     * 增加用户（用户注册）
     * 参数校验:用户电话 用户邮箱 用户名不能重复存在;
     * <p>
     * 用户注册时选择角色
     *
     * @param user
     * @throws Exception
     */

    public Boolean insertUser(User user) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.USER);

            if (user == null || user.getUserName() == null || user.getUserTelephone() == null || user.getUserSysEmail() == null) {
                logMessage.build(LogEnum.USER_EMPTY);
                log.warn(logMessage.log());

            } else {

                MysqlBuilder<User> insertUser = new MysqlBuilder<>(User.class);
                insertUser.setIn(user);

                MysqlBuilder<User> builder = new MysqlBuilder<>(User.class);
                User user1 = new User();
                user1.setUserName(user.getUserName());
                User user1Flag = baseMysqlComp.selectOne(builder.buildIn(user1));

                User user2 = new User();
                user2.setUserTelephone(user.getUserTelephone());
                User user2Flag = baseMysqlComp.selectOne(builder.buildIn(user2));

                User user3 = new User();
                user3.setUserSysEmail(user.getUserSysEmail());
                User user3Flag = baseMysqlComp.selectOne(builder.buildIn(user3));

                if (user1Flag != null) {
                    log.warn("用户名已存在，请重新输入");
                } else if (user2Flag != null) {
                    log.warn("用户电话已存在，请重新输入");

                } else if (user3Flag != null) {
                    log.warn("用户邮箱已存在，请重新输入");
                } else {
                    user.setUserId(UuidGenerator.getCustomUuid(System.currentTimeMillis()).toString());
                    user.setUserPassword(PasswordEncrypt.hashPassword(user.getUserPassword()));
                    baseMysqlComp.insert(insertUser);
                    //给用户分配角色
                    UserRoleRef userRoleRef = new UserRoleRef();
                    userRoleRef.setRefUserId(user.getUserId());
                    Role role = new Role();
                    role.setRoleName(user.getRoleName());
                    MysqlBuilder<Role> flag = new MysqlBuilder<>(Role.class);
                    flag.setIn(role);
                    Role role1 = baseMysqlComp.selectOne(flag);
                    userRoleRef.setRefRoleId(role1.getRoleId());
                    roleMapperImpl.grantRoleToUser(userRoleRef);

                    return true;

                }

            }
        } catch (Exception e) {
            log.error("Failed to insert user!", e);
        }
        return null;
    }

    /**
     * 查找用户信息
     * 参数校验：可通过用户名、用户电话、用户id、用户邮箱找到用户；
     *
     * @param user
     * @throws Exception
     */
    public User selectOneUser(User user) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.USER);
            if (user == null || (user.getUserName() == null && user.getUserTelephone() == null && user.getUserSysEmail() == null)) {
                logMessage.build(LogEnum.USER_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<User> selectOneUser = new MysqlBuilder<>(User.class);
                selectOneUser.setIn(user);
                if (baseMysqlComp.selectOne(selectOneUser) == null) {
                    logMessage.build(LogEnum.USER_NO_Exists);
                    log.error(logMessage.log());

                } else {
                    return baseMysqlComp.selectOne(selectOneUser);

                }
            }
        } catch (Exception e) {
            log.error("Failed to select user!", e);
        }
        return null;
    }

    /**
     * 查询用户列表信息
     *
     * @return
     * @throws Exception
     */

    public List<User> selectAllUser() throws Exception {
        MysqlBuilder<User> selectAllUser = new MysqlBuilder<>(User.class);
        return baseMysqlComp.selectList(selectAllUser);
    }

    /**
     * 删除角色（注销）
     * 应该获取当前用户信息再注销，而不是输入id
     * 这里还没有完成获取当前用户的功能
     * 参数校验:角色id不为空；
     *
     * @param user
     * @throws Exception
     */
    public Boolean deleteUser(User user) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.USER);
            if (user == null) {
                logMessage.build(LogEnum.USER_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<User> deleteUser = new MysqlBuilder<>(User.class);
                deleteUser.setIn(user);
                if (baseMysqlComp.selectOne(deleteUser) == null) {
                    logMessage.build(LogEnum.USER_NO_Exists);
                    log.error(logMessage.log());
                } else {
                    baseMysqlComp.delete(deleteUser);
                    return true;
                }

            }
        } catch (Exception e) {
            log.error("Failed to delete user!", e);
        }
        return null;
    }

    /**
     * 更新角色信息
     * 参数校验：用户标识信息不为空
     *
     * @param user
     * @throws Exception
     */

    public Boolean updateUser(User user) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.USER);
            if (user == null) {
                logMessage.build(LogEnum.USER_EMPTY);
                log.warn(logMessage.log());
            } else {
                if (user.getUserName() != null) {
                    User one = new User();
                    one.setUserName(user.getUserName());
                    MysqlBuilder<User> checkUser = new MysqlBuilder<>(User.class);
                    checkUser.setIn(one);
                    if (baseMysqlComp.selectOne(checkUser) == null) {
                        logMessage.build(LogEnum.USER_NO_Exists);
                        log.error(logMessage.log());
                    }
                    MysqlBuilder<User> updateUser = new MysqlBuilder<>(User.class);
                    User user1 = baseMysqlComp.selectOne(checkUser);
                    updateUser.setIn(user1);
                    updateUser.setUpdate(user);
                    baseMysqlComp.update(updateUser);
                } else if (user.getUserTelephone() != null) {
                    User two = new User();
                    two.setUserTelephone(user.getUserTelephone());
                    MysqlBuilder<User> checkUser = new MysqlBuilder<>(User.class);
                    checkUser.setIn(two);
                    if (baseMysqlComp.selectOne(checkUser) == null) {
                        logMessage.build(LogEnum.USER_NO_Exists);
                        log.error(logMessage.log());
                    }
                    MysqlBuilder<User> updateUser = new MysqlBuilder<>(User.class);
                    User user1 = baseMysqlComp.selectOne(checkUser);
                    updateUser.setIn(user1);
                    updateUser.setUpdate(user);
                    baseMysqlComp.update(updateUser);
                } else if (user.getUserSysEmail() != null) {
                    User three = new User();
                    three.setUserSysEmail(user.getUserSysEmail());
                    MysqlBuilder<User> checkUser = new MysqlBuilder<>(User.class);
                    checkUser.setIn(three);
                    if (baseMysqlComp.selectOne(checkUser) == null) {
                        logMessage.build(LogEnum.USER_NO_Exists);
                        log.error(logMessage.log());
                    }
                    MysqlBuilder<User> updateUser = new MysqlBuilder<>(User.class);
                    User user1 = baseMysqlComp.selectOne(checkUser);
                    updateUser.setIn(user1);
                    updateUser.setUpdate(user);
                    baseMysqlComp.update(updateUser);
                    return true;
                }

            }


        } catch (Exception e) {
            log.error("Failed to update user!", e);
        }
        return null;
    }

    /**
     * 验证登陆
     * 参数校验 登陆信息不为空
     * 用户账号可以是用户名
     *
     * @param user
     * @return
     */

    public Boolean loginUserByName(User user) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.USER);
            if (user == null || (user.getUserName() == null && user.getUserTelephone() == null && user.getUserSysEmail() == null)) {
                logMessage.build(LogEnum.USER_EMPTY);
                log.warn(logMessage.log());
            }
            user.setUserPassword(PasswordEncrypt.hashPassword(user.getUserPassword()));
            MysqlBuilder<User> builder = new MysqlBuilder<>(User.class);
            User user1 = new User();
            user1.setUserName(user.getUserName());
            User user1Flag = baseMysqlComp.selectOne(builder.buildIn(user1));
            if (user1Flag == null) {
                log.warn("用户名不存在，请重新输入");
            } else {

                MysqlBuilder<User> loginUser = new MysqlBuilder<>(User.class);
                loginUser.setIn(user);
                if (baseMysqlComp.selectOne(loginUser) != null) {
                    return true;
                } else {
                    return false;
                }


            }
        } catch (Exception e) {
            log.error("Failed to loginUser!", e);
        }
        return null;

    }

    /**
     * 验证登陆
     * 参数校验 登陆信息不为空
     * 用户账号可以是用户邮箱
     *
     * @param user
     * @return
     * @throws Exception
     */
    public Boolean loginUserByEmail(User user) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.USER);
            if (user == null || (user.getUserName() == null && user.getUserTelephone() == null && user.getUserSysEmail() == null)) {
                logMessage.build(LogEnum.USER_EMPTY);
                log.warn(logMessage.log());
            }
            user.setUserPassword(PasswordEncrypt.hashPassword(user.getUserPassword()));
            MysqlBuilder<User> builder = new MysqlBuilder<>(User.class);
            User user1 = new User();
            user1.setUserSysEmail(user.getUserSysEmail());
            User user1Flag = baseMysqlComp.selectOne(builder.buildIn(user1));
            if (user1Flag == null) {
                log.warn("用户邮箱不存在，请重新输入");
            } else {
                MysqlBuilder<User> loginUser = new MysqlBuilder<>(User.class);
                loginUser.setIn(user);
                if (baseMysqlComp.selectOne(loginUser) != null) {
                    return true;
                } else {
                    return false;
                }

            }
        } catch (Exception e) {
            log.error("Failed to loginUserByEmail!", e);
        }
        return null;
    }

    /**
     * 验证登陆
     * 参数校验 登陆信息不为空
     * 用户账号可以是用户电话
     *
     * @param user
     * @return
     * @throws Exception
     */
    public Boolean loginUserByTelephone(User user) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.USER);
            if (user == null || (user.getUserName() == null && user.getUserTelephone() == null && user.getUserSysEmail() == null)) {
                logMessage.build(LogEnum.USER_EMPTY);
                log.warn(logMessage.log());
            }
            user.setUserPassword(PasswordEncrypt.hashPassword(user.getUserPassword()));
            MysqlBuilder<User> builder = new MysqlBuilder<>(User.class);

            User user1 = new User();
            user1.setUserTelephone(user.getUserTelephone());
            User user1Flag = baseMysqlComp.selectOne(builder.buildIn(user1));
            if (user1Flag == null) {
                log.warn("用户电话不存在，请重新输入");
            } else {
                MysqlBuilder<User> loginUser = new MysqlBuilder<>(User.class);
                loginUser.setIn(user);
                if (baseMysqlComp.selectOne(loginUser) != null) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("Failed to loginUserByTelephone!", e);
        }
        return false;
    }

    /**
     * 判断用户的角色
     * 参数校验 用户信息不为空(预设了用户已经有角色)
     * 用户存在，用户已赋予角色
     *
     * @param user
     * @return
     * @throws Exception
     */

    public Short isUserWhatToRole(User user) throws Exception {
        try {
            LogComp.LogMessage logMessage = LogComp.buildData(LogType.USER);
            if (user == null) {
                logMessage.build(LogEnum.USER_EMPTY);
                log.warn(logMessage.log());
            } else {
                MysqlBuilder<User> isUserWhatToRole = new MysqlBuilder<>(User.class);
                isUserWhatToRole.setIn(user);
                if (baseMysqlComp.selectOne(isUserWhatToRole) == null) {
                    logMessage.build(LogEnum.USER_NO_Exists);
                    log.error(logMessage.log());
                } else {
                    UserRoleRef userRoleRef1 = new UserRoleRef();
                    userRoleRef1.setRefUserId(user.getUserId());
                    MysqlBuilder<UserRoleRef> builder = new MysqlBuilder<>(UserRoleRef.class);
                    builder.setIn(userRoleRef1);
                    return baseMysqlComp.selectOne(builder).getRefRoleId();
                }
            }

        } catch (Exception e) {
            log.error("Failed to isUserWhatToRole!", e);
        }
        return null;
    }


}

