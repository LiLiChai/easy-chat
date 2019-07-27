package pers.fancy.chat.bootstrap.verify;

import com.alibaba.fastjson.JSONArray;

/**
 * 用户校验
 *
 * @author 李醴茝
 */
public interface VerifyService {

    /**
     * 验证token是否有效
     *
     * @param token
     * @return
     */
    boolean verifyToken(String token);

    /**
     * 获取分组中所有用户ID
     *
     * @param groupId
     * @return
     */
    JSONArray getArrayByGroupId(String groupId);

}