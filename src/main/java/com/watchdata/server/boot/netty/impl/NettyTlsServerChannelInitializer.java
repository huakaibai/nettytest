package com.watchdata.server.boot.netty.impl;

import com.watchdata.common.netty.coder.TlsDecoder;
import com.watchdata.common.netty.config.TlsServerConfig;
import com.watchdata.server.ServerHandler;
import com.watchdata.common.netty.handler.SocketManagerInitHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zhibin.wang
 * @desc netty channel pipeline 初始化
 **/
@Component
public class NettyTlsServerChannelInitializer extends ChannelInitializer<Channel> {

    @Autowired
    private TlsServerConfig config;

    @Autowired
    private TlsDecoder tlsDecoder;

    @Autowired
    private SocketManagerInitHandler socketManagerInitHandler;

    @Autowired
    private ServerHandler serverHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new LengthFieldPrepender(4, false));
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

        ch.pipeline().addLast(new IdleStateHandler(config.getCheckTime(),
                config.getCheckTime(), config.getCheckTime(), TimeUnit.MILLISECONDS));

        ch.pipeline().addLast(tlsDecoder);
        ch.pipeline().addLast(socketManagerInitHandler);
        ch.pipeline().addLast(serverHandler);

    }
}
