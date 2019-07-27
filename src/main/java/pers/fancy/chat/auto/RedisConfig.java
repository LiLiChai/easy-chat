package pers.fancy.chat.auto;

import pers.fancy.chat.common.constant.LogConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * 关于redis一些基础东西
 */
public class RedisConfig {

    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

    /** 单例模式 */
    private static RedisConfig instance = new RedisConfig();

    public static Jedis jedis;

    /**
     * 如果配置启动分布式，则自动初始化jedis
     * */
    private RedisConfig(){
        if (ConfigFactory.initNetty.getDistributed()){
            jedis = new Jedis(ConfigFactory.RedisIP);
            log.info(LogConstant.REDIS_START + jedis.ping());
        }
    }

    public static RedisConfig getInstance(){
        return instance;
    }

}
