package pers.fancy.chat.bootstrap;

import pers.fancy.chat.common.bean.InitNetty;


/**
 * @author 李醴茝
 */
public interface BootstrapServer {

    /**
     * 服务关闭
     */
    void shutdown();

    /**
     * 初始化设置
     * @param serverBean
     */
    void setServerBean(InitNetty serverBean);

    /**
     * 服务开启
     */
    void start();

}
