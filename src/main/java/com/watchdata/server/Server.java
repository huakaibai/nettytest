package com.watchdata.server;

import com.watchdata.common.netty.coder.TlsDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;


/**
 * @author zhibin.wang
 * @create 2018-12-17 13:34
 * @desc 服务端
 **/
@Component
public class Server {

    public   void start (){

        //服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        EventLoopGroup boss = new NioEventLoopGroup();

        EventLoopGroup worker = new NioEventLoopGroup();

        //业务类线程池
        final EventLoopGroup handler = new NioEventLoopGroup();

        try {
            //设置线程池
            bootstrap.group(boss,worker);

            // 设置socket工厂
            bootstrap.channel(NioServerSocketChannel.class);

            //设置管道工厂
            bootstrap.childHandler(new ChannelInitializer<Channel>() {
                protected void initChannel(Channel ch) throws Exception {
              ch.pipeline().addLast(new TlsDecoder());
//                    ch.pipeline().addLast(new StringEncoder());
//                    UseService useService = new UseService();
//                    ClientHelloHandler clientHelloHandler = new ClientHelloHandler();
//                    TlsMessageHandler tlsMessageHandler = new TlsMessageHandler();
//                    clientHelloHandler.setUseService(useService);
//                    tlsMessageHandler.setUseService(useService);
//                    ch.pipeline().addLast(clientHelloHandler);
//                    ch.pipeline().addLast(tlsMessageHandler);
                   ch.pipeline().addLast(handler,new ServerHandler());

                }
            });

            //设置TCP参数
            bootstrap.option(ChannelOption.SO_BACKLOG,2048);//设置链接缓冲池的大小
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true
            ); //维持链接的活跃，清除死链接
            bootstrap.childOption(ChannelOption.TCP_NODELAY,true); // 关闭延迟下载

            //绑定端口
            ChannelFuture future = bootstrap.bind(10101);
            System.out.println("Server start");

            //等待服务端关闭
            future.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
