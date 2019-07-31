package pers.fancy.chat.bootstrap.channel;

import com.alibaba.fastjson.JSONArray;
import pers.fancy.chat.bootstrap.backmsg.ChatBackMapService;
import pers.fancy.chat.bootstrap.backmsg.ChatBackMapServiceImpl;
import pers.fancy.chat.bootstrap.channel.http.HttpChannelService;
import pers.fancy.chat.bootstrap.channel.http.HttpChannelServiceImpl;
import pers.fancy.chat.bootstrap.channel.ws.WebSocketChannelService;
import pers.fancy.chat.common.base.HandlerService;
import pers.fancy.chat.common.bean.SendChat;
import pers.fancy.chat.common.bean.vo.SendServerVO;
import pers.fancy.chat.common.constant.CommonConstant;
import com.google.gson.Gson;
import pers.fancy.chat.bootstrap.channel.ws.WsChannelService;
import pers.fancy.chat.bootstrap.verify.VerifyService;
import pers.fancy.chat.task.DataAsynchronousTask;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author 李醴茝
 */
public class HandlerServiceImpl extends HandlerService {

    private final VerifyService inChatVerifyService;

    private final ChatBackMapService inChatBackMapService = new ChatBackMapServiceImpl();

    private final HttpChannelService httpChannelService = new HttpChannelServiceImpl();

    private final WsChannelService webSocketChannelService = new WebSocketChannelService();

    private final DataAsynchronousTask dataAsynchronousTask;

    public HandlerServiceImpl(DataAsynchronousTask dataAsynchronousTask, VerifyService inChatVerifyService) {
        this.dataAsynchronousTask = dataAsynchronousTask;
        this.inChatVerifyService = inChatVerifyService;
    }


    @Override
    public void getList(Channel channel) {
        httpChannelService.getList(channel);
    }

    @Override
    public void getSize(Channel channel) {
        httpChannelService.getSize(channel);
    }

    @Override
    public void sendFromServer(Channel channel, SendServerVO serverVO) {
        httpChannelService.sendFromServer(channel,serverVO);
    }

    @Override
    public void sendInChat(Channel channel, FullHttpMessage msg) {
        System.out.println(msg);
        String content = msg.content().toString(CharsetUtil.UTF_8);
        Gson gson = new Gson();
        SendChat sendInChat = gson.fromJson(content, SendChat.class);
        httpChannelService.sendByInChat(channel,sendInChat);
    }

    @Override
    public void notFindUri(Channel channel) {
        httpChannelService.notFindUri(channel);
    }

    @Override
    public boolean login(Channel channel, Map<String,Object> maps) {
        //校验规则，自定义校验规则
        return check(channel, maps);
    }

    @Override
    public void sendMeText(Channel channel, Map<String,Object> maps) {
        Gson gson = new Gson();
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendMe((String) maps.get(CommonConstant.VALUE)))));
        try {
            dataAsynchronousTask.writeData(maps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendToText(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String otherOne = (String) maps.get(CommonConstant.ONE);
        String value = (String) maps.get(CommonConstant.VALUE);
        String token = (String) maps.get(CommonConstant.TOKEN);
        //返回给自己
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendBack(otherOne,value))));
        if (webSocketChannelService.hasOther(otherOne)){
            //发送给对方--在线
            Channel other = webSocketChannelService.getChannel(otherOne);
            if (other == null){
                //转http分布式
                httpChannelService.sendInChat(otherOne,inChatBackMapService.getMsg(token,value));
            }else{
                other.writeAndFlush(new TextWebSocketFrame(
                        gson.toJson(inChatBackMapService.getMsg(token,value))));
            }
        }else {
            maps.put(CommonConstant.ON_ONLINE,otherOne);
        }
        try {
            dataAsynchronousTask.writeData(maps);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void sendGroupText(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String groupId = (String) maps.get(CommonConstant.GROUP_ID);
        String token = (String) maps.get(CommonConstant.TOKEN);
        String value = (String) maps.get(CommonConstant.VALUE);
        List<String> no_online = new ArrayList<>();
        JSONArray array = inChatVerifyService.getArrayByGroupId(groupId);
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendGroup(token,value,groupId))));
        for (Object item:array) {
            if (!token.equals(item)){
                if (webSocketChannelService.hasOther((String) item)){
                    Channel other = webSocketChannelService.getChannel((String) item);
                    if (other == null){
                        //转http分布式
                        httpChannelService.sendInChat((String) item,inChatBackMapService.sendGroup(token,value,groupId));
                    }else{
                        other.writeAndFlush(new TextWebSocketFrame(
                                gson.toJson(inChatBackMapService.sendGroup(token,value,groupId))));
                    }
                }else{
                    no_online.add((String) item);
                }
            }
        }
        maps.put(CommonConstant.ONLINE_GROUP,no_online);
        try {
            dataAsynchronousTask.writeData(maps);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void verify(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String token = (String) maps.get(CommonConstant.TOKEN);
        System.out.println(token);
        if (inChatVerifyService.verifyToken(token)){
            return;
        }else{
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginError())));
            close(channel);
        }
    }

    @Override
    public void sendPhotoToMe(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        System.out.println(maps.get(CommonConstant.VALUE));
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendMe((String) maps.get(CommonConstant.VALUE)))));
        try {
            dataAsynchronousTask.writeData(maps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean check(Channel channel, Map<String, Object> maps){
        Gson gson = new Gson();
        String token = (String) maps.get(CommonConstant.TOKEN);
        if (inChatVerifyService.verifyToken(token)){
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginSuccess())));
            webSocketChannelService.loginWsSuccess(channel,token);
            return true;
        }
        channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginError())));
        close(channel);
        return false;
    }

    @Override
    public void close(Channel channel) {
        webSocketChannelService.close(channel);
    }
}
