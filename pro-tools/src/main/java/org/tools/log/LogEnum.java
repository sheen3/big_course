package org.tools.log;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */

public enum LogEnum {
    //---------------------LoginServer---------------------
    //测试
    TEST_OK("测试", "无敌"),


    //系统
    PARAM_ERROR("参数异常", "参数异常"),
    SYSTEM_ERROR("系统错误", "系统错误"),


    //拦截器
    NO_CONTROLLER("拦截器错误", "没有此接口"),
    NO_TOKEN("拦截器错误", "没有携带token"),
    TOKEN_ERROR("拦截器错误", "token无效"),
    TOKEN_EXPIRES("拦截器错误", "token已过期"),


    NO_CHECK("拦截器日志", "访问无CHECK接口"),
    CHECK("拦截器日志", "访问有CHECK接口"),
    NO_PRODUCER("拦截器日志", "访问无Producer接口"),
    NO_Consumer("拦截器日志", "访问无Consumer接口"),
    PRODUCER("拦截器日志", "访问有Producer接口"),
    Consumer("拦截器日志", "访问有Consumer接口"),
    CHECK_OK("拦截器日志", "token验证成功"),


    //登录
    LOGIN_OK("登录", "登录成功"),
    UPDATE_IP("登录", "Ip地址发生变化"),
    LOGIN_TEL_PWD_ERROR("登录", "电话或密码错误"),
    LOGIN_EMAIL_PWD_ERROR("登录", "邮箱或密码错误"),
    LOGIN_USERNAME_PWD_ERROR("登录", "用户名或密码错误"),
    LOGIN_TOKEN_ERROR("登录", "token验证失败"),
    LOGIN_VERIFY_ERROR("登录验证码", "验证码错误"),


    //注册
    REGISTER_TEL_EXIST("注册", "此电话已存在"),
    REGISTER_OK("注册", "注册成功"),


    //过滤操作
    FILTER_MSG("过滤操作", "token不为空"),
    FILTER_MSG_ERROR("过滤操作", "token为空"),
    FILTER_MSG_OK("过滤操作", "token验证通过"),

    //身份验证操作
    IDENTITY_administrators("验证操作", "管理员访问"),
    IDENTITY_USER("验证操作", "普通用户访问被拦截"),


    //Exception
    EXCEPTION_FORMAT_ERROR("内部错误", "格式转换异常"),


    //---------------------MainServer---------------------
    //工具类报错

    FILE_TO_BYTE_ERROR("文件转byte流", "抛错了 瞅瞅"),

    //Task
    TASK_EXIST("添加任务", "任务已存在"),
    TASK_NOT_FOUND("查询任务", "任务不存在"),
    P_TASK_START_OK("生产者开始任务", "生产者开始任务成功"),
    P_TASK_EXIST("生产者开始任务", "任务已经开始"),
    P_TASK_NOT_FOUND("生产者开始任务", "任务不存在"),
    P_TASK_NO_START("生产者结束任务", "任务未开始"),

    P_TASK_ADD_OK("生产者发布任务", "发布成功"),
    P_GET_ADD_TASKS_NO_USER("生产者获取发布的任务", "该用户不存在"),

    C_TAKE_IS_TAKE("消费者接受任务", "已接受该任务"),
    C_TAKE_TASK_OK("消费者接受任务", "接受成功"),
    C_START_NO_TAKE("消费者开始任务", "没有接受该任务"),
    C_START_TASK_NOT_START("消费者开始任务", "该任务没有开始"),
    C_START_TASK_OK("消费者接受任务", "任务开始成功"),
    C_END_TASK_NO_DOING("消费者结束任务", "没有正在执行的任务"),
    C_END_TASK_OK("消费者结束任务", "任务结束成功"),
    C_DEL_TASK_NO_EXIST("消费者放弃任务", "该用户没有接受该任务"),
    C_DEL_TASK_OK("消费者放弃任务", "任务放弃成功"),

    USER_NOT_FOUND("获取用户信息失败", "用户不存在"),
    PUBLIC_CONDUCT_NOT_FOUND("获取任务进行时失败", "任务进行时不存在"),

    //数据接口:PROD
    ACCESS_DATA_INTERFACE_ERROR("访问数据接口", "访问数据解析错误"),
    ACCESS_DATA_INTERFACE_NULL("访问数据接口", "访问数据为空"),
    ADD_TASK_USERID_TRUE("添加任务", "UserId无误，任务添加成功"),
    ADD_TASK_USERID_FLOAT("添加任务", "UserId错误，添加任务失败"),
    END_TASK_USERID_TRUE("关闭任务", "数据解析成功,任务关闭成功"),
    END_TASK_USERID_FLOAT("关闭任务", "数据解析失败,关闭任务失败"),
    PROD_TASK_START_TRUE("开始任务", "数据解析成功，任务开始成功"),
    PROD_TASK_START_FLOAT("开始任务", "数据解析失败，任务开始失败"),
    PROD_GetResult_TRUE("获取任务测试结果集", "数据解析成功，获取结果成功"),
    PROD_GetResult_FLOAT("获取任务测试结果集", "数据解析失败，获取结果失败"),
    PROD_Get_Task_TRUE("获取任务信息", "数据解析成功，获取任务信息结果成功"),
    PROD_Get_Task_FLOAT("获取任务信息", "数据解析失败，获取任务信息结果失败"),
    PROD_GET_ALL_ADD_TASKS_TRUE("获取发布的所有任务的详细信息", "数据解析成功，获取所有任务信息结果成功"),
    PROD_GET_ALL_ADD_TASKS_FLOAT("获取任务信息", "数据解析失败，获取所有任务信息结果失败"),

    //CONS_
    CONS_TAKE_TASk_TRUE("消费者接受任务", "数据解析成功，消费者接受任务成功"),
    CONS_TAKE_TASk_FLOAT("消费者接受任务", "数据解析失败，消费者接受任务失败"),
    CONS_START_TASk_TRUE("消费者开始任务", "数据解析成功，消费者开始任务成功"),
    CONS_START_TASk_FLOAT("消费者开始任务", "数据解析失败，消费者开始任务失败"),
    CONS_END_TASk_TRUE("消费者结束任务", "数据解析成功，消费者结束任务成功"),
    CONS_END_TASk_FLOAT("消费者结束任务", "数据解析失败，消费者结束任务失败"),
    CONS_DEL_TASk_TRUE("消费者放弃任务", "数据解析成功，消费者放弃任务成功"),
    CONS_DEL_TASk_FLOAT("消费者放弃任务", "数据解析失败，消费者放弃任务失败"),
    CONS_ALL_GET_TASkS_TRUE("消费者获取接受的所有任务的详细信息", "数据解析成功，消费者获取接受的所有任务的详细信息成功"),
    CONS_ALL_GET_TASkS_FLOAT("消费者放弃任务", "数据解析失败，消费者获取接受的所有任务的详细信息失败"),

    //定时器
    SCHEDULED_EXECUTE_START("任务开始执行", "成功"),
    SCHEDULED_EXECUTE_OVER("任务执行完成", "成功"),

    //Lru
    LRU_GET_LIST_OK("获取List", "成功"),
    LRU_GET_VALUE_NULL("获取value", "该任务Id不存在"),
    LRU_GET_VALUE_OK("获取value", "成功"),
    LRU_PUT_TASK_OK("存放TASK", "成功"),


    //KAFKA
    KAFKA_CONSUMER_TOPIC_CLOSE_NO_TOPIC("关闭kafka监听器", "该topic不存在"),
    KAFKA_CONSUMER_TOPIC_CLOSE_OK("关闭kafka监听器", "成功"),
    KAFKA_CONSUMER_TOPIC_START_ERROR("启动监听线程", "线程启动失败"),
    KAFKA_CONSUMER_TOPIC_START_OK("启动监听线程", "线程启动成功"),

    KAFKA_CONSUMER_TOPIC_START_EXIST_OK("启动监听线程", "该topic存在,关闭"),

    KAFKA_CONSUMER_TOPIC_GET_EXIST("获取监听线程", "该队列已存在,直接返回"),
    KAFKA_CONSUMER_TOPIC_GET_NO_EXIST("获取监听线程", "该队列不存在,初始化一个"),
    KAFKA_PRODUCER_CREATE_IS_EXIST("创建producer对象", "该对象已存在"),
    KAFKA_PRODUCER_CREATE_SUCCESS("创建producer对象", "创建成功"),

    KAFKA_PRODUCER_CLOSE_NOT_EXIST("关闭producer对象", "该对象不存在"),
    KAFKA_PRODUCER_CLOSE_SUCCESS("关闭producer对象", "关闭成功"),

    KAFKA_PRODUCER_SEND_NO_PRODUCER("发送信息", "producer对象不存在"),
    KAFKA_PRODUCER_SEND_SUCCESS("发送信息", "发送成功"),

    CONTROL_START_S2C_TOPIC("启动同步调用推送", "启动"),
    CONTROL_RE_CONTROL_ERROR_CONDUCT_NOT_EXIST("重新分配任务", "进行时上下文不存在"),

    CONTROL_RE_CONTROL_ERROR_KAFKA_CONTEXT_NOT_EXIST("重新分配任务", "任务上下文不存在"),

    CONTROL_ALL_START("重新分配所有正在进行的任务", "开始重新分配所有正在进行的任务"),
    CONTROL_ALL_END("重新分配所有正在进行的任务", "重新分配所有正在进行的任务结束"),
    CONTROL_ONE_START("重新分配一个正在进行的任务", "开始重新分配一个正在进行的任务"),
    CONTROL_ONE_END("重新分配一个正在进行的任务", "重新分配一个正在进行的任务结束"),

    //权限

    POWER_EMPTY("权限操作", "权限信息为空"),
    POWER_EXISTS("权限操作", "权限已存在"),
    POWER_Is_Null("权限操作", "权限Id为空"),
    POWER_NO_EXISTS("权限操作", "权限不存在"),

    //角色
    ROLE_EMPTY("角色操作", "角色信息为空"),
    ROLE_EXISTS("角色操作", "角色已存在"),
    ROLE_Is_Null("角色操作", "角色Id为空"),
    ROLE_NO_EXISTS("角色操作", "角色不存在"),

    ROLE_USER_EMPTY("角色用户操作","角色或用户为空" ),
    ROLE_POWER_EMPTY("角色权限操作","角色或权限为空"),
    ROLE_HAS_POWER("角色权限操作","角色已有权限无需分配"),
    ROLE_NO_POWER("角色权限操作","角色未拥有该权限无需撤销"),

    USER_EMPTY("用户操作","用户信息为空" ),
    USER_EXISTS("用户操作","用户已存在" ),
    USER_Is_Null("用户操作","用户Id为空" ),
    USER_NO_Exists("用户操作","用户不存在" ),
    USER_HAS_ROLE("用户操作","用户已有角色无需分配" ),
    USER_NO_ROLE("用户操作","该用户无此角色无需撤销" ),

    PRODUCT_EMPTY("产品操作","产品信息为空" ),
    PRODUCT_EXISTS("产品操作","产品已存在" ),
    PRODUCT_NO_EXISTS("产品操作","产品不存在" ),
    QRCODE_EMPTY("二维码操作","二维码信息为空" ),
    QRCODE_EXISTS("二维码操作","二维码已存在" ),
    QRCODE_NO_EXISTS("二维码操作","二维码不存在" ),
    LOGISTIC_EMPTY("物流操作","物流信息为空" ),
    LOGISTIC_EXISTS("物流操作","物流已存在" ),
    LOGISTIC_NO_EXISTS("物流操作","物流不存在" ),
    SUPERMARKET_EMPTY("超市操作","超市信息为空" ),
    SUPERMARKET_EXISTS("超市操作","超市已存在" ),
    SUPERMARKET_NO_EXISTS("超市操作","超市不存在" )

    ;




    private String type;
    private String msg;

    LogEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
