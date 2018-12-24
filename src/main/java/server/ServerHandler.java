package server;

import common.Util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @author zhibin.wang
 * @create 2018-12-17 13:48
 * @desc
 **/

public class ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {



    //private UseService useService = new UseService();
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf  s) throws Exception {

        System.out.println("收到新消息");
        //useService.messageProcess(s);
        byte[] req = new byte[s.readableBytes()];
        s.readBytes(req);
        System.out.println(StringUtil.byte2hex(req));
      System.out.println(req.length);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("新用户加入");
        super.channelActive(ctx);
    }
}
