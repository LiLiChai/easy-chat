package pers.fancy.chat.common.constant;


/**
 * @author 李醴茝
 */
public interface LogConstant {

    String HTTPCHANNELSERVICEIMPL_NOTFINDLOGIN = "[HttpChannelServiceImpl.sendFromServer] 未找到用户在线标识";

    String HTTPCHANNELSERVICEIMPL_CLOSE = "[HttpChannelServiceImpl.close] 关闭HTTP通道连接";

    String HTTPCHANNELSERVICEIMPL_SEND_EXCEPTION = "[HttpChannelServiceImpl.sendFromServer] 发送通知异常";

    String DEFAULTWEBSOCKETHANDLER_GETSIZE = "[DefaultWebSocketHandler.httpdoMessage.GET_SIZE1]";

    String DEFAULTWEBSOCKETHANDLER_SENDFROMSERVER = "[DefaultWebSocketHandler.httpdoMessage.SEND_FROM_SERVER1]";

    String DEFAULTWEBSOCKETHANDLER_NOTFINDURI = "[DefaultWebSocketHandler.httpdoMessage.NOT_FIND_URI]";

    String DEFAULTWEBSOCKETHANDLER_GETLIST = "[DefaultWebSocketHandler.httpdoMessage.GET_LIST1]";

    String DEFAULTWEBSOCKETHANDLER_SENDINCHAT = "[DefaultWebSocketHandler.httpdoMessage.SEND_INCHAT1]";

    String DEFAULTWEBSOCKETHANDLER_LOGIN = "[DefaultWebSocketHandler.textdoMessage.LOGIN]";

    String DEFAULTWEBSOCKETHANDLER_SENDME = "[DefaultWebSocketHandler.textdoMessage.SEND_ME]";

    String DefaultWebSocketHandler_SENDTO = "[DefaultWebSocketHandler.textdoMessage.SEND_TO]";

    String DEFAULTWEBSOCKETHANDLER_SENDGROUP = "[DefaultWebSocketHandler.textdoMessage.SEND_GROUP]";

    String CHANNELACTIVE = "[DefaultWebSocketHandler.channelActive]";

    String CHANNEL_SUCCESS = "链接成功";

    String DISCONNECT = "异常断开";

    String EXCEPTIONCAUGHT = "[DefaultWebSocketHandler.exceptionCaught]";

    String CHANNELINACTIVE = "[Handler：channelInactive]";

    String CLOSE_SUCCESS = "关闭成功";

    String NOTFINDLOGINCHANNLEXCEPTION = "[捕获异常：NotFindLoginChannelException]-[Handler：channelInactive] 关闭未正常注册链接！";

    String DATAASYNCHRONOUSTASK_01 = "[DataAsynchronousTask.writeData]:数据外抛异常";

    String DATAASYNCHRONOUSTASK_02 = "[DataAsynchronousTask.writeData]:数据外抛异常";

    String DATAASYNCHRONOUSTASK_03 = "[DataAsynchronousTask.writeData]:线程任务执行异常";

    String REDIS_START = "[RedisConfig.getJedis]:连接成功，测试连接PING->";
}
