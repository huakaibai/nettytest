package com.watchdata.server;

import com.watchdata.common.ClietnHello;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @author zhibin.wang
 * @create 2018-12-17 13:48
 * @desc
 **/

public class ClientHelloHandler extends SimpleChannelInboundHandler<ClietnHello> {



    private UseService useService ;




    public UseService getUseService() {
        return useService;
    }

    public void setUseService(UseService useService) {
        this.useService = useService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ClietnHello clietnHello) throws Exception {

        System.out.println("收到新消息");
        System.out.println(clietnHello);
        useService.messageProcess("clientHello userService");
    }
}
