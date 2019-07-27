package pers.fancy.chat.bootstrap.data;

import pers.fancy.chat.common.bean.InChatMessage;


/**
 * @author 李醴茝
 */
public interface InChatToDataBaseService {

    Boolean writeMessage(InChatMessage message);

}
