package org.tools.common.uuid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */
@Component
public class SystemUuidComp {
    /**
     * 没有办法反解析回去了 只能用再一些没有含义的UUID上 BY YJJ
     * 或者可以自己给他拼上 -
     * 他的格式是这样的: FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF
     * @return UUID
     */
    public String genUuid(){
        UUID uuid = UUID.randomUUID();
        return StringUtils.remove(uuid.toString(), '-');
    }

    /**
     * @param username 用户名
     * @return 带-的uuid 可以通过uuid反解出来username
     * 他其实就是把数据md5了 然后再生成uuid 想要反解 第一步把uuid反解成md5 再反解md5 数据就出来了
     */
    public String genUserUuidByUserName(String username){
        UUID uuid = UUID.nameUUIDFromBytes(username.getBytes(StandardCharsets.UTF_8));
        return uuid.toString();
    }
}
