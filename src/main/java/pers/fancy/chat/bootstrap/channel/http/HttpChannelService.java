package pers.fancy.chat.bootstrap.channel.http;

import pers.fancy.chat.common.bean.SendChat;
import pers.fancy.chat.common.bean.vo.SendServerVO;
import io.netty.channel.Channel;

import java.util.Map;


/**
 * @author 李醴茝
 */
public interface HttpChannelService {

    /**
     * 获取在线用户数
     *
     * @param channel
     */
    void getSize(Channel channel);

    /**
     * 服务端向用户发送通知
     *
     * @param channel
     * @param serverVO
     */
    void sendFromServer(Channel channel, SendServerVO serverVO);

    /**
     * @param channel
     */
    void notFindUri(Channel channel);

    /**
     * @param channel
     */
    void close(Channel channel);

    /**
     * @param channel
     */
    void getList(Channel channel);

    /**
     * @param token
     * @param msg
     */
    void sendInChat(String token, Map msg);

    /**
     * @param channel
     * @param sendInChat
     */
    void sendByInChat(Channel channel, SendChat sendInChat);

}
