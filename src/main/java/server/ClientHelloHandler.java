package server;

import common.ClietnHello;
import common.Util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @author zhibin.wang
 * @create 2018-12-17 13:48
 * @desc
 **/

public class ClientHelloHandler extends SimpleChannelInboundHandler<ClietnHello> {



    private UseService useService ;
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ClietnHello  s) throws Exception {

        System.out.println("收到新消息");
        System.out.println(s);
        useService.messageProcess("clientHello userService");


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("新用户加入 tlsss");
        super.channelActive(ctx);
    }

    public UseService getUseService() {
        return useService;
    }

    public void setUseService(UseService useService) {
        this.useService = useService;
    }
}
