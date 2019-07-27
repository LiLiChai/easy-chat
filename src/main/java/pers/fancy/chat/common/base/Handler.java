package pers.fancy.chat.common.base;

import pers.fancy.chat.common.constant.LogConstant;
import pers.fancy.chat.common.exception.NotFindLoginChannelException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty实现初始层
 *
 * @author 李醴茝
 */
public abstract class Handler extends SimpleChannelInboundHandler<Object> {

    private static final Logger log = LoggerFactory.getLogger(Handler.class);

    HandlerApi handlerApi;

    public Handler(HandlerApi handlerApi) {
        this.handlerApi = handlerApi;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            log.info("TextWebSocketFrame" + msg);
            textdoMessage(ctx, (TextWebSocketFrame) msg);
        } else if (msg instanceof WebSocketFrame) {
            log.info("WebSocketFrame" + msg);
            webdoMessage(ctx, (WebSocketFrame) msg);
        } else if (msg instanceof FullHttpRequest) {
            log.info("FullHttpRequest" + msg);
            httpdoMessage(ctx, (FullHttpRequest) msg);
        }
    }

    protected abstract void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg);

    protected abstract void textdoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg);

    protected abstract void httpdoMessage(ChannelHandlerContext ctx, FullHttpRequest msg);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(LogConstant.CHANNELINACTIVE + ctx.channel().localAddress().toString() + LogConstant.CLOSE_SUCCESS);
        try {
            handlerApi.close(ctx.channel());
        } catch (NotFindLoginChannelException e) {
            log.error(LogConstant.NOTFINDLOGINCHANNLEXCEPTION);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if(evt instanceof IdleStateEvent){
//            webSocketHandlerApi.doTimeOut(ctx.channel(),(IdleStateEvent)evt);
//        }
        super.userEventTriggered(ctx, evt);
    }
}
