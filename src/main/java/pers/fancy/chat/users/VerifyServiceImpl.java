package pers.fancy.chat.users;

import com.alibaba.fastjson.JSONArray;
import pers.fancy.chat.bootstrap.verify.VerifyService;


/**
 * @author 李醴茝
 */
public class VerifyServiceImpl implements VerifyService {


    @Override
    public boolean verifyToken(String token) {
        return true;
    }


    @Override
    public JSONArray getArrayByGroupId(String groupId) {
        //获取每个分组中的用户ID数量
        JSONArray jsonArray = JSONArray.parseArray("[\"1111\",\"2222\",\"333\"]");
        return jsonArray;
    }
}
