package pers.fancy.chat.bootstrap;


import pers.fancy.chat.auto.ConfigFactory;
import pers.fancy.chat.bootstrap.channel.HandlerServiceImpl;
import pers.fancy.chat.bootstrap.handler.DefaultHandler;
import pers.fancy.chat.common.bean.InitNetty;
import pers.fancy.chat.common.constant.BootstrapConstant;
import pers.fancy.chat.common.constant.NotInChatConstant;
import pers.fancy.chat.common.ssl.SecureSocketSslContextFactory;
import pers.fancy.chat.common.util.SslUtil;
import pers.fancy.chat.task.DataAsynchronousTask;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.SystemPropertyUtil;
import org.apache.commons.lang3.ObjectUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.security.KeyStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author 李醴茝
 */
public abstract class AbstractBootstrapServer implements BootstrapServer {

    private String PROTOCOL = "TLS";

    private SSLContext SERVER_CONTEXT;

    /**
     * @param channelPipeline channelPipeline
     * @param serverBean      服务配置参数
     */
    protected void initHandler(ChannelPipeline channelPipeline, InitNetty serverBean) {
        if (serverBean.isSsl()) {
            if (!ObjectUtils.allNotNull(serverBean.getJksCertificatePassword(), serverBean.getJksFile(), serverBean.getJksStorePassword())) {
                throw new NullPointerException(NotInChatConstant.SSL_NOT_FIND);
            }
            try {
                SSLContext context = SslUtil.createSSLContext("JKS", serverBean.getJksFile(), serverBean.getJksStorePassword());
                SSLEngine engine = context.createSSLEngine();
                engine.setUseClientMode(false);
                engine.setNeedClientAuth(false);
                channelPipeline.addLast(BootstrapConstant.SSL, new SslHandler(engine));
                System.out.println("open ssl  success");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        intProtocolHandler(channelPipeline, serverBean);
        channelPipeline.addLast(new IdleStateHandler(serverBean.getHeart(), 0, 0));
        channelPipeline.addLast(new DefaultHandler(new HandlerServiceImpl(new DataAsynchronousTask(ConfigFactory.inChatToDataBaseService), ConfigFactory.inChatVerifyService)));
    }

    private void intProtocolHandler(ChannelPipeline channelPipeline, InitNetty serverBean) {
        channelPipeline.addLast(BootstrapConstant.HTTP_CODE, new HttpServerCodec());
//        channelPipeline.addLast("http-decoder",new HttpRequestDecoder());
        channelPipeline.addLast(BootstrapConstant.AGGREGATOR, new HttpObjectAggregator(serverBean.getMaxContext()));
//        channelPipeline.addLast("http-encoder",new HttpResponseEncoder());
        channelPipeline.addLast(BootstrapConstant.CHUNKED_WRITE, new ChunkedWriteHandler());
        channelPipeline.addLast(BootstrapConstant.WEB_SOCKET_HANDLER, new WebSocketServerProtocolHandler(serverBean.getWebSocketPath()));
    }

    private void initSsl(InitNetty serverBean) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
        });
        String algorithm = SystemPropertyUtil.get("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }
        SSLContext serverContext;
        try {
            //
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(SecureSocketSslContextFactory.class.getResourceAsStream(serverBean.getJksFile()),
                    serverBean.getJksStorePassword().toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(ks, serverBean.getJksCertificatePassword().toCharArray());
            serverContext = SSLContext.getInstance(PROTOCOL);
            serverContext.init(kmf.getKeyManagers(), null, null);
        } catch (Exception e) {
            throw new Error(
                    "Failed to initialize the server-side SSLContext", e);
        }
        SERVER_CONTEXT = serverContext;
    }

}
