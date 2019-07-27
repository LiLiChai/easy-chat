package pers.fancy.chat.auto;

import pers.fancy.chat.bootstrap.channel.http.FromServerService;
import pers.fancy.chat.bootstrap.data.InChatToDataBaseService;
import pers.fancy.chat.bootstrap.verify.InChatVerifyService;
import pers.fancy.chat.common.bean.InitNetty;

/**
 * 默认配置工厂
 */
public class ConfigFactory {

    public static String RedisIP;

    /** 用户校验伪接口 */
    public static InChatVerifyService inChatVerifyService;

    /** 用户获取数据伪接口 */
    public static InChatToDataBaseService inChatToDataBaseService;

    /** 系统信息枚举服务接口 */
    public static FromServerService fromServerService;

    /** InChat项目配置 */
    public static InitNetty initNetty;

}