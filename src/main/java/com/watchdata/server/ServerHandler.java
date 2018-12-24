package com.watchdata.server;

import com.watchdata.common.BaseTls;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import com.watchdata.server.scanner.Invoker;
import com.watchdata.server.scanner.InvokerHoler;


/**
 * @author zhibin.wang
 * @create 2018-12-17 13:48
 * @desc
 **/

public class ServerHandler extends SimpleChannelInboundHandler<BaseTls> {



    private UseService useService = new UseService();
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, BaseTls  baseTls) throws Exception {

        System.out.println(Thread.currentThread().getName());
        Invoker invoker = InvokerHoler.getInvoker(baseTls.getType());
        invoker.invoker(useService,baseTls.getObject());

        /*System.out.println("收到新消息");
        //useService.messageProcess(s);
        byte[] req = new byte[s.readableBytes()];
        s.readBytes(req);
        System.out.println(StringUtil.byte2hex(req));
      System.out.println(req.length);*/

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("新用户加入");
        super.channelActive(ctx);
    }
}
