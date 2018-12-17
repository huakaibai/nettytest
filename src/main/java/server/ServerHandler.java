package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executors;

/**
 * @author zhibin.wang
 * @create 2018-12-17 13:48
 * @desc
 **/
public class ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {



    private UseService useService = new UseService();
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf s) throws Exception {

     //   useService.messageProcess(s);
        byte[] req = new byte[s.readableBytes()];
        s.readBytes(req);

        System.out.println(req.length);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("新用户加入");
        super.channelActive(ctx);
    }
}
