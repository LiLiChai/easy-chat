package pers.fancy.chat.users;

import pers.fancy.chat.bootstrap.data.InChatToDataBaseService;
import pers.fancy.chat.common.bean.InChatMessage;


/**
 * @author 李醴茝
 */
public class DataBaseServiceImpl implements InChatToDataBaseService {
    @Override
    public Boolean writeMessage(InChatMessage inChatMessage) {
        System.out.println(inChatMessage.toString());
        return true;
    }
}
