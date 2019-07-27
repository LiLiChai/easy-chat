package pers.fancy.chat.users;

import pers.fancy.chat.bootstrap.data.MessagePersistService;
import pers.fancy.chat.common.bean.ChatMessage;


/**
 * @author 李醴茝
 */
public class MessagePersistServiceImpl implements MessagePersistService {
    @Override
    public Boolean writeMessage(ChatMessage inChatMessage) {
        System.out.println(inChatMessage.toString());
        return true;
    }
}
