package pers.fancy.chat.common.utils;

import pers.fancy.chat.common.constant.UtilConstant;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * @author 李醴茝
 */
public class SslUtil {

    private static volatile SSLContext sslContext = null;

    public static SSLContext createSSLContext(String type, String path, String password) throws Exception {
        if (null == sslContext) {
            synchronized (SslUtil.class) {
                if (null == sslContext) {
                    /// "JKS"
                    KeyStore ks = KeyStore.getInstance(type);
                    /// 证书存放地址// 　　　  　　
                    InputStream ksInputStream = new FileInputStream(UtilConstant.PATH_PREFIX + path);
                    ks.load(ksInputStream, password.toCharArray());
                    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                    kmf.init(ks, password.toCharArray());
                    sslContext = SSLContext.getInstance(UtilConstant.INSTANT);
                    sslContext.init(kmf.getKeyManagers(), null, null);
                }
            }
        }
        return sslContext;
    }

}
