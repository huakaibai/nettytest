package com.watchdata.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhibin.wang
 * @create 2018-12-17 16:37
 * @desc
 **/
public class ClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("客户端收到信息"+s);
    }
}
