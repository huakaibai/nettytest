package com.watchdata.client.boot.netty.handler;

import com.watchdata.client.boot.netty.NettyClientTlsInitializerImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.net.SocketAddress;

/**
 * @author zhibin.wang
 * @desc 客户端重试hanler
 **/
@Component
@Slf4j
@ChannelHandler.Sharable
public class NettyTlsClientRetryHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        //这里进行断线重连，一般断线重连由客户端进行
        reConnect(ctx);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);

        if (cause instanceof ConnectException){

            reConnect(ctx);
        }

    }

    private void reConnect(ChannelHandlerContext ctx) {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        log.error("断线重连 socketAddress:{} ", socketAddress);
        NettyClientTlsInitializerImpl.reConnect(socketAddress);
    }
}
