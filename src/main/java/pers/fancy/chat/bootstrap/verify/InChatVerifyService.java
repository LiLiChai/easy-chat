package pers.fancy.chat.bootstrap.verify;

import com.alibaba.fastjson.JSONArray;

/**
 * 用户校验层
 */
public interface InChatVerifyService {

    boolean verifyToken(String token);

    JSONArray getArrayByGroupId(String groupId);

}