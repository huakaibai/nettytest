package com.watchdata.server;

import com.watchdata.common.TlsMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @author zhibin.wang
 * @create 2018-12-17 13:48
 * @desc
 **/

public class TlsMessageHandler extends SimpleChannelInboundHandler<TlsMessage> {

    private UseService useService ;

    //private UseService useService = new UseService();





    public UseService getUseService() {
        return useService;
    }

    public void setUseService(UseService useService) {
        this.useService = useService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TlsMessage tlsMessage) throws Exception {
        System.out.println("收到新消息");
        System.out.println(tlsMessage);
        useService.messageProcess("TlsMessageHandler UserService");
    }
}
