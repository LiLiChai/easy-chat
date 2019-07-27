package pers.fancy.chat.bootstrap;

import pers.fancy.chat.auto.AutoConfig;
import pers.fancy.chat.auto.RedisConfig;
import pers.fancy.chat.common.ip.IpUtils;
import pers.fancy.chat.common.bean.InitNetty;
import pers.fancy.chat.common.utils.RemotingUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author 李醴茝
 */
public class NettyBootstrapServer extends AbstractBootstrapServer {

    private final Logger log = LoggerFactory.getLogger(NettyBootstrapServer.class);

    private InitNetty serverBean;

    @Override
    public void setServerBean(InitNetty serverBean) {
        this.serverBean = serverBean;
    }

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    // 启动辅助类
    ServerBootstrap bootstrap = null;

    /**
     * 服务开启
     */
    @Override
    public void start() {
        initEventPool();
        bootstrap.group(bossGroup, workGroup)
                .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, serverBean.isReuseaddr())
                .option(ChannelOption.SO_BACKLOG, serverBean.getBacklog())
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_RCVBUF, serverBean.getRevbuf())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        initHandler(ch.pipeline(), serverBean);
                    }
                })
                .childOption(ChannelOption.TCP_NODELAY, serverBean.isNodelay())
                .childOption(ChannelOption.SO_KEEPALIVE, serverBean.isKeepalive())
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.bind(IpUtils.getHost(), serverBean.getWebport()).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                log.info("服务端启动成功【" + IpUtils.getHost() + ":" + serverBean.getWebport() + "】");
                AutoConfig.address = IpUtils.getHost() + ":" + serverBean.getWebport();
                RedisConfig.getInstance();
            } else {
                log.info("服务端启动失败【" + IpUtils.getHost() + ":" + serverBean.getWebport() + "】");
            }
        });
    }

    /**
     * 初始化EnentPool 参数
     */
    private void initEventPool() {
        bootstrap = new ServerBootstrap();
        if (useEpoll()) {
            bossGroup = new EpollEventLoopGroup(serverBean.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new EpollEventLoopGroup(serverBean.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_WORK_" + index.incrementAndGet());
                }
            });

        } else {
            bossGroup = new NioEventLoopGroup(serverBean.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new NioEventLoopGroup(serverBean.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "WORK_" + index.incrementAndGet());
                }
            });
        }
    }

    /**
     * 关闭资源
     */
    @Override
    public void shutdown() {
        if (workGroup != null && bossGroup != null) {
            try {
                // 优雅关闭
                bossGroup.shutdownGracefully().sync();
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                log.error("服务端关闭资源失败【" + IpUtils.getHost() + ":" + serverBean.getWebport() + "】");
            }
        }
    }

    private boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform() && Epoll.isAvailable();
    }

}
