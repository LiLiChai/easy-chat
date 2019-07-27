package pers.fancy.chat.users;

import com.alibaba.fastjson.JSONArray;
import pers.fancy.chat.bootstrap.verify.InChatVerifyService;


/**
 * @author 李醴茝
 */
public class VerifyServiceImpl implements InChatVerifyService {


    @Override
    public boolean verifyToken(String token) {
        return true;
    }


    @Override
    public JSONArray getArrayByGroupId(String groupId) {
        JSONArray jsonArray = JSONArray.parseArray("[\"1111\",\"2222\",\"333\"]");
        return jsonArray;
    }
}
