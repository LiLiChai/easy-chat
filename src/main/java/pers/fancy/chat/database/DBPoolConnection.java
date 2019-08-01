package pers.fancy.chat.database;

/**
 * 功能描述：
 *
 * @author lihuan
 * @version V1.0 创建时间：2019/7/31 19:13
 * Copyright 2019 by landray & STIC
 */
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

/**
 * 要实现单例模式，保证全局只有一个数据库连接池
 */
public class DBPoolConnection {

    static Logger log = Logger.getLogger(DBPoolConnection.class);
    private static DBPoolConnection dbPoolConnection = null;
    private static DruidDataSource druidDataSource = null;

    static {
        Properties properties = loadPropertiesFile("db.properties");
        try {
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
            log.info("数据源配置成功！");
        } catch (Exception e) {
            log.error("获取配置失败！");
        }
    }

    /**
     * 数据库连接池单例
     *
     * @return
     */
    public static synchronized DBPoolConnection getInstance() {
        if (null == dbPoolConnection) {
            dbPoolConnection = new DBPoolConnection();
        }
        return dbPoolConnection;
    }

    /**
     * 返回druid数据库连接
     *
     * @return
     * @throws SQLException
     */
    public DruidPooledConnection getConnection() throws SQLException {
        return druidDataSource.getConnection();
    }

    /**
     * @param fullFile 配置文件名
     * @return Properties对象
     */
    private static Properties loadPropertiesFile(String fullFile) {
        if (null == fullFile || fullFile.equals("")) {
            throw new IllegalArgumentException("Properties file path can not be null" + fullFile);
        }
        InputStream inputStream = DBPoolConnection.class.getClassLoader().getResourceAsStream(fullFile);
        Properties p = null;
        try {
            p = new Properties();
            p.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return p;
    }

}