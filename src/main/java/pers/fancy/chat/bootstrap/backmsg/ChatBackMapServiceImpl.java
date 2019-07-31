package pers.fancy.chat.bootstrap.backmsg;

import pers.fancy.chat.common.constant.CommonConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 列入项目中，默认返回实现
 *
 * @author 李醴茝
 */
public class ChatBackMapServiceImpl implements ChatBackMapService {


    @Override
    public Map<String, String> loginSuccess() {
        Map<String, String> backMap = new HashMap<>(0);
        backMap.put(CommonConstant.TYPE, CommonConstant.LOGIN);
        backMap.put(CommonConstant.SUCCESS, CommonConstant.TRUE);
        return backMap;
    }


    @Override
    public Map<String, String> loginError() {
        Map<String, String> backMap = new HashMap<>(0);
        backMap.put(CommonConstant.TYPE, CommonConstant.LOGIN);
        backMap.put(CommonConstant.SUCCESS, CommonConstant.FALSE);
        return backMap;
    }


    @Override
    public Map<String, String> sendMe(String value) {
        Map<String, String> backMap = new HashMap<>(0);
        backMap.put(CommonConstant.TYPE, CommonConstant.SEND_ME);
        backMap.put(CommonConstant.VALUE, value);
        return backMap;
    }


    @Override
    public Map<String, String> sendBack(String otherOne, String value) {
        Map<String, String> backMap = new HashMap<>(0);
        backMap.put(CommonConstant.TYPE, CommonConstant.SEND_TO);
        backMap.put(CommonConstant.VALUE, value);
        backMap.put(CommonConstant.ONE, otherOne);
        return backMap;
    }


    @Override
    public Map<String, String> getMsg(String token, String value) {
        Map<String, String> backMap = new HashMap<>(0);
        backMap.put(CommonConstant.TYPE, CommonConstant.SEND_TO);
        backMap.put(CommonConstant.FROM, token);
        backMap.put(CommonConstant.VALUE, value);
        return backMap;
    }


    @Override
    public Map<String, String> sendGroup(String token, String value, String groupId) {
        Map<String, String> backMap = new HashMap<>(0);
        backMap.put(CommonConstant.TYPE, CommonConstant.SEND_GROUP);
        backMap.put(CommonConstant.FROM, token);
        backMap.put(CommonConstant.VALUE, value);
        backMap.put(CommonConstant.GROUP_ID, groupId);
        return backMap;
    }

}
