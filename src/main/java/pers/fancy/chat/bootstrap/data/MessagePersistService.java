package pers.fancy.chat.bootstrap.data;

import pers.fancy.chat.common.bean.ChatMessage;


/**
 * @author 李醴茝
 */
public interface MessagePersistService {

    /**
     * IM消息持久化处理
     *
     * @param message
     * @return
     */
    Boolean writeMessage(ChatMessage message);

}
