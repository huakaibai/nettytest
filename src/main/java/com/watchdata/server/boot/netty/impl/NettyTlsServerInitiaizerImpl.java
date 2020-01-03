package com.watchdata.server.boot.netty.impl;

import com.watchdata.common.netty.config.TlsServerConfig;
import com.watchdata.server.boot.NettyTlsInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zhibin.wang
 * @desc
 **/
@Component
@Slf4j
public class NettyTlsServerInitiaizerImpl implements NettyTlsInitializer, DisposableBean {

    private final NettyTlsServerChannelInitializer nettyTlsServerChannelInitializer;
    private final TlsServerConfig tlsServerConfig;
    private EventLoopGroup workerGroup;
    private EventLoopGroup bossGroup;

    @Autowired
    public NettyTlsServerInitiaizerImpl(NettyTlsServerChannelInitializer nettyTlsServerChannelInitializer, TlsServerConfig tlsServerConfig) {
        this.nettyTlsServerChannelInitializer = nettyTlsServerChannelInitializer;
        this.tlsServerConfig = tlsServerConfig;
    }

    @Override
    public void init() {

        workerGroup = new NioEventLoopGroup();
        bossGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(nettyTlsServerChannelInitializer);
            if (StringUtils.hasText(tlsServerConfig.getIp())){
                b.bind(tlsServerConfig.getIp(),tlsServerConfig.getPort());
            }else {
                b.bind(tlsServerConfig.getPort());

            }


            log.info("Socket started on {}:{} ",
                    StringUtils.hasText(tlsServerConfig.getIp()) ? tlsServerConfig.getIp() : "0.0.0.0", tlsServerConfig.getPort());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void destroy() throws Exception {
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }

        log.info("server was down.");
    }
}
