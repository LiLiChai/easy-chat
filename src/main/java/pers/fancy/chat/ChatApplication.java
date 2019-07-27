package pers.fancy.chat;

import pers.fancy.chat.auto.ConfigFactory;
import pers.fancy.chat.auto.InitServer;
import pers.fancy.chat.users.DataBaseServiceImpl;
import pers.fancy.chat.users.FromServerServiceImpl;
import pers.fancy.chat.users.MyInit;
import pers.fancy.chat.users.VerifyServiceImpl;


public class ChatApplication {

    public static void main(String[] args) {
        ConfigFactory.initNetty = new MyInit();
//        ConfigFactory.inChatVerifyService = new VerifyServiceImpl();
//        ConfigFactory.inChatToDataBaseService = new DataBaseServiceImpl();
//        ConfigFactory.fromServerService = FromServerServiceImpl.TYPE2;
//        ConfigFactory.RedisIP = "192.168.1.119";
//        ConfigFactory.RedisIP = "192.168.1.119";

        InitServer.open();
    }

}
