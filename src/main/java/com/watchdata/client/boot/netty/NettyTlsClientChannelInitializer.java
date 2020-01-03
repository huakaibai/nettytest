package com.watchdata.client.boot.netty;

import com.watchdata.client.boot.netty.handler.NettyTlsClientRetryHandler;
import com.watchdata.common.netty.coder.TlsDecoder;
import com.watchdata.common.netty.handler.SocketManagerInitHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhibin.wang
 * @desc Tls 客户端初始化通道
 **/
@Component
public class NettyTlsClientChannelInitializer extends ChannelInitializer<Channel> {

    @Autowired
    private TlsDecoder tlsDecoder;


    @Autowired
    private NettyTlsClientRetryHandler nettyTlsClientRetryHandler;

    @Autowired
    private SocketManagerInitHandler socketManagerInitHandler;
    @Override
    protected void initChannel(Channel ch) throws Exception {

        ch.pipeline().addLast(new LengthFieldPrepender(4, false));
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                0, 4, 0, 4));

        ch.pipeline().addLast(tlsDecoder);
        ch.pipeline().addLast(nettyTlsClientRetryHandler);
        ch.pipeline().addLast(socketManagerInitHandler);

    }
}
