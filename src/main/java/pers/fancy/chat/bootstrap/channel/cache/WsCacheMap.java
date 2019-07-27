package pers.fancy.chat.bootstrap.channel.cache;

import pers.fancy.chat.auto.AutoConfig;
import pers.fancy.chat.auto.ConfigFactory;
import pers.fancy.chat.auto.RedisConfig;
import pers.fancy.chat.common.exception.NotFindLoginChannelException;
import pers.fancy.chat.common.constant.NotInChatConstant;
import pers.fancy.chat.common.utils.RedisUtil;
import io.netty.channel.Channel;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket链接实例本地存储
 *
 * @author 李醴茝
 */
public class WsCacheMap {

    /**
     * 存储用户标识与链接实例
     */
    private final static Map<String, Channel> maps = new ConcurrentHashMap<>();

    /**
     * 存储链接地址与用户标识
     */
    private final static Map<String, String> addMaps = new ConcurrentHashMap<>();

    /**
     * Redis连接实例
     */
    private final static Jedis jedis = RedisConfig.jedis;

    /**
     * 是否启动分布式
     */
    private final static Boolean isDistributed = ConfigFactory.initNetty.getDistributed();

    private final static String address = AutoConfig.address;

    /**
     * 存储链接
     *
     * @param token   {@link String} 用户标签
     * @param channel {@link Channel} 链接实例
     */
    public static void saveWs(String token, Channel channel) {
        maps.put(token, channel);
        if (isDistributed) {
            jedis.set(token, RedisUtil.convertMD5(address, token));
        }
    }

    /**
     * 存储登录信息
     *
     * @param address 登录地址
     * @param token   用户标签
     */
    public static void saveAd(String address, String token) {
        addMaps.put(address, token);
    }

    /**
     * 获取链接数据
     *
     * @param token {@link String} 用户标识
     * @return {@link Channel} 链接实例
     */
    public static Channel getByToken(String token) {
        if (isDistributed) {
            if (!maps.containsKey(token)) {
                //转分布式发送
                return null;
            }
        }
        return maps.get(token);
    }

    /**
     * 获取对应token标签
     *
     * @param address {@link String} 链接地址
     * @return {@link String}
     */
    public static String getByAddress(String address) {
        return addMaps.get(address);
    }

    /**
     * 删除链接数据
     *
     * @param token {@link String} 用户标识
     */
    public static void deleteWs(String token) {
        try {
            maps.remove(token);
            if (isDistributed) {
                jedis.del(token);
            }
        } catch (NullPointerException e) {
            throw new NotFindLoginChannelException(NotInChatConstant.Not_Login);
        }
    }

    /**
     * 删除链接地址
     *
     * @param address
     */
    public static void deleteAd(String address) {
        addMaps.remove(address);
    }

    /**
     * 获取链接数
     *
     * @return {@link Integer} 链接数
     */
    public static Integer getSize() {
        if (isDistributed) {
            return jedis.keys("*").size();
        }
        return maps.size();
    }

    /**
     * 判断是否存在链接账号
     *
     * @param token {@link String} 用户标识
     * @return {@link Boolean} 是否存在
     */
    public static boolean hasToken(String token) {
        if (isDistributed) {
            return jedis.exists(token);
        }
        return maps.containsKey(token);
    }

    /**
     * 获取在线用户标签列表
     *
     * @return {@link Set} 标识列表
     */
    public static Set<String> getTokenList() {
        if (isDistributed) {
            return jedis.keys("*");
        }
        Set keys = maps.keySet();
        return keys;
    }

    public static String getByJedis(String token) {
        return jedis.get(token);
    }

}
