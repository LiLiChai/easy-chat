package pers.fancy.chat.common.util;

import pers.fancy.chat.common.bean.ChatMessage;
import pers.fancy.chat.common.constant.CommonConstant;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


/**
 * @author 李醴茝
 */
public class MessageChangeUtil {

    public static ChatMessage Change(Map<String, Object> maps) {
        ChatMessage message = new ChatMessage();
        if (maps.containsKey(CommonConstant.TOKEN)) {
            message.setToken((String) maps.get(CommonConstant.TOKEN));
        }
        if (maps.containsKey(CommonConstant.TIME)) {
            message.setTime((Date) maps.get(CommonConstant.TIME));
        }
        if (maps.containsKey(CommonConstant.VALUE)) {
            message.setValue((String) maps.get(CommonConstant.VALUE));
        }
        if (maps.containsKey(CommonConstant.TYPE)) {
            message.setType((String) maps.get(CommonConstant.TYPE));
        }
        if (maps.containsKey(CommonConstant.ONE)) {
            message.setOne((String) maps.get(CommonConstant.ONE));
        }
        if (maps.containsKey(CommonConstant.GROUP_ID)) {
            message.setGroudId((String) maps.get(CommonConstant.GROUP_ID));
        }
        if (maps.containsKey(CommonConstant.ON_ONLINE)) {
            message.setOnline((String) maps.get(CommonConstant.ON_ONLINE));
        }
        if (maps.containsKey(CommonConstant.ONLINE_GROUP)) {
            message.setOnlineGroup((ArrayList) maps.get(CommonConstant.ONLINE_GROUP));
        }
        return message;
    }

}
