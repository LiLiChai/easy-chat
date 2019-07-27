package pers.fancy.chat.common.base;

import io.netty.channel.Channel;

import java.util.Map;


/**
 * @author lihuan
 */
public interface HandlerApi {

    void close(Channel channel);

}
