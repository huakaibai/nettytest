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
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TlsMessage  s) throws Exception {

        System.out.println("收到新消息");
        System.out.println(s);
        useService.messageProcess("TlsMessageHandler UserService");
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("新用户加入");
        super.channelActive(ctx);
    }


    public UseService getUseService() {
        return useService;
    }

    public void setUseService(UseService useService) {
        this.useService = useService;
    }
}
