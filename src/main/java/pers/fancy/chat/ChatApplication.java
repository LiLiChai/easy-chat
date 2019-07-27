package pers.fancy.chat;

import pers.fancy.chat.auto.ConfigFactory;
import pers.fancy.chat.auto.InitServer;
import pers.fancy.chat.users.ChatConfigInit;
import pers.fancy.chat.users.FromServerServiceImpl;
import pers.fancy.chat.users.MessagePersistServiceImpl;
import pers.fancy.chat.users.VerifyServiceImpl;


/**
 * @author 李醴茝
 */
public class ChatApplication {

    public static void main(String[] args) {
        ConfigFactory.initNetty = new ChatConfigInit();
        ConfigFactory.inChatVerifyService = new VerifyServiceImpl();
        ConfigFactory.inChatToDataBaseService = new MessagePersistServiceImpl();
        ConfigFactory.fromServerService = FromServerServiceImpl.TYPE2;
        ConfigFactory.RedisIP = "192.168.1.119";
        ConfigFactory.RedisIP = "192.168.1.119";

        InitServer.open();
    }

}
