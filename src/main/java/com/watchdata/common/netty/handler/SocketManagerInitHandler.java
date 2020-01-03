package com.watchdata.common.netty.handler;

import com.watchdata.common.netty.bean.SocketManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhibin.wang
 * @desc  通道管理初始化用于客户端初始化连接记录
 **/
@Component
@Slf4j
@ChannelHandler.Sharable
public class SocketManagerInitHandler extends ChannelInboundHandlerAdapter {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // 添加渠道管理
        SocketManager.getInstance().addChannel(ctx.channel());
        log.info("添加渠道--"+ctx.channel().remoteAddress().toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        SocketManager.getInstance().removeChannel(ctx.channel());

        log.info("渠道断开连接--"+ctx.channel().remoteAddress().toString());
    }




    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        // 心跳这块优化下优化发送心跳消息 TODO
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                ctx.writeAndFlush("心跳");
            }
        }
    }
}
