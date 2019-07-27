package pers.fancy.chat.bootstrap;

import pers.fancy.chat.common.bean.InitNetty;


/**
 * @author 李醴茝
 */
public interface BootstrapServer {

    void shutdown();

    void setServerBean(InitNetty serverBean);

    void start();

}
