package pers.fancy.chat.auto;


import pers.fancy.chat.bootstrap.BootstrapServer;
import pers.fancy.chat.bootstrap.NettyBootstrapServer;
import pers.fancy.chat.common.bean.InitNetty;

/**
 * Chat项目启动服务
 *
 * @author 李醴茝
 */
public class InitServer {


    /** 静态化处理，保证唯一，确保用户启动的是他自己指定的，不是框架的一个数据配置集合*/
    private static InitNetty serverBean = ConfigFactory.initNetty;

    /**
     * netty服务器启动切面
     */
    static BootstrapServer bootstrapServer;


    public static void open() {
        if (serverBean != null) {
            bootstrapServer = new NettyBootstrapServer();
            bootstrapServer.setServerBean(serverBean);
            bootstrapServer.start();
        }
    }

    /**
     * 关闭服务
     */
    public void close() {
        if (bootstrapServer != null) {
            bootstrapServer.shutdown();
        }
    }

}
