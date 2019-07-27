package pers.fancy.chat.bootstrap.handler;

import com.alibaba.fastjson.JSON;
import pers.fancy.chat.common.base.Handler;
import pers.fancy.chat.common.base.HandlerApi;
import pers.fancy.chat.common.base.HandlerService;
import pers.fancy.chat.common.bean.vo.SendServerVO;
import pers.fancy.chat.common.constant.CommonConstant;
import pers.fancy.chat.common.constant.HttpConstant;
import pers.fancy.chat.common.constant.LogConstant;
import pers.fancy.chat.common.constant.NotInChatConstant;
import pers.fancy.chat.common.exception.NoFindHandlerException;
import pers.fancy.chat.common.util.HttpUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;


/**
 * @author 李醴茝
 */
@ChannelHandler.Sharable
public class DefaultHandler extends Handler {

    private final Logger log = LoggerFactory.getLogger(DefaultHandler.class);

    private final HandlerApi handlerApi;

    public DefaultHandler(HandlerApi handlerApi) {
        super(handlerApi);
        this.handlerApi = handlerApi;
    }

    @Override
    protected void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {
        Channel channel = ctx.channel();
        HandlerService httpHandlerService;
        if (handlerApi instanceof HandlerService) {
            httpHandlerService = (HandlerService) handlerApi;
        } else {
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }
        if (msg instanceof BinaryWebSocketFrame) {
            //TODO 实现图片处理
        }
    }

    @Override
    protected void httpdoMessage(ChannelHandlerContext ctx, FullHttpRequest msg) {
        Channel channel = ctx.channel();
        HandlerService httpHandlerService;
        if (handlerApi instanceof HandlerService) {
            httpHandlerService = (HandlerService) handlerApi;
        } else {
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }
        switch (pers.fancy.chat.common.util.HttpUtil.checkType(msg)) {
            case HttpConstant.GET_SIZE1:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETSIZE);
                httpHandlerService.getSize(channel);
                break;
            case HttpConstant.SEND_FROM_SERVER1:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDFROMSERVER);
                SendServerVO serverVO = null;
                try {
                    serverVO = HttpUtil.getToken(msg);
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
                httpHandlerService.sendFromServer(channel, serverVO);
                break;
            case HttpConstant.GET_LIST1:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETLIST);
                httpHandlerService.getList(channel);
                break;
            case HttpConstant.SEND_INCHAT1:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDINCHAT);
                httpHandlerService.sendInChat(channel, msg);
                break;
            case HttpConstant.NOT_FIND_URI:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_NOTFINDURI);
                httpHandlerService.notFindUri(channel);
                break;
            default:
                System.out.println("为匹配" + msg);
                break;
        }
    }

    @Override
    protected void textdoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Channel channel = ctx.channel();
        HandlerService handlerService;
        if (handlerApi instanceof HandlerService) {
            handlerService = (HandlerService) handlerApi;
        } else {
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }
        Map<String, Object> maps = (Map) JSON.parse(msg.text());
        maps.put(CommonConstant.TIME, new Date());
        switch ((String) maps.get(CommonConstant.TYPE)) {
            case CommonConstant.LOGIN:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_LOGIN);
                handlerService.login(channel, maps);
                break;
            //针对个人，发送给自己
            case CommonConstant.SEND_ME:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDME);
                handlerService.verify(channel, maps);
                handlerService.sendMeText(channel, maps);
                break;
            //针对个人，发送给某人
            case CommonConstant.SEND_TO:
                log.info(LogConstant.DefaultWebSocketHandler_SENDTO);
                handlerService.verify(channel, maps);
                handlerService.sendToText(channel, maps);
                break;
            //发送给群组
            case CommonConstant.SEND_GROUP:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDGROUP);
                handlerService.verify(channel, maps);
                handlerService.sendGroupText(channel, maps);
                break;
            //发送图片，发送给自己
            case CommonConstant.SEND_PHOTO_TO_ME:
                log.info("图片到个人");
                handlerService.verify(channel, maps);
                handlerService.sendPhotoToMe(channel, maps);
                break;
            default:
                break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(LogConstant.CHANNELACTIVE + ctx.channel().remoteAddress().toString() + LogConstant.CHANNEL_SUCCESS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(LogConstant.EXCEPTIONCAUGHT + ctx.channel().remoteAddress().toString() + LogConstant.DISCONNECT);
        ctx.close();
    }
}
