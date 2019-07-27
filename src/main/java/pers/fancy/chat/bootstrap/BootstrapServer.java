package pers.fancy.chat.bootstrap;

import pers.fancy.chat.common.bean.InitNetty;


public interface BootstrapServer {

    void shutdown();

    void setServerBean(InitNetty serverBean);

    void start();

}
