package com.watchdata.server;

import com.watchdata.common.netty.business.TlsAnswer;
import com.watchdata.common.netty.bean.BaseTls;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author zhibin.wang
 * @create 2018-12-17 13:48
 * @desc
 **/
@Component
public class ServerHandler extends SimpleChannelInboundHandler<BaseTls> {



    @Autowired
    private TlsAnswer serverTlsAnswer;



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseTls baseTls) throws Exception {

        serverTlsAnswer.answer(baseTls);
              /*
Invoker invoker = InvokerHoler.getInvoker(baseTls.getType());
        invoker.invoker(useService,baseTls.getObject());
     System.out.println("收到新消息");
        useService.messageProcess(s);
        byte[] req = new byte[s.readableBytes()];
        s.readBytes(req);
        System.out.println(StringUtil.byte2hex(req));
      System.out.println(req.length);*/
    }
}
