package com.watchdata.client;

import com.watchdata.common.Util.StringUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author zhibin.wang
 * @create 2018-12-17 16:31
 * @desc
 **/
public class Client {

    public static void main(String[] args){

        Bootstrap bootstrap = new Bootstrap();

        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //设置线程池
            bootstrap.group(worker);

            bootstrap.channel(NioSocketChannel.class);

            bootstrap.handler(new ChannelInitializer<Channel>() {
                protected void initChannel(Channel ch) throws Exception {
//                  ch.pipeline().addLast(new StringDecoder());
//                 ch.pipeline().addLast(new StringEncoder());
                  ch.pipeline().addLast(new ClientHandler());
                }
            });

            ChannelFuture connect = bootstrap.connect("127.0.0.1",10101);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String clientHellow = "160303003C010000380303EEEB2BDC099E8F4AEE02302673F9B12A5C369CCC6274A316BE8E29F9A1F3A73A00000A008B008C002C00AE00B0010000050001000102";
            String tlsMessage = "170303000101";
            byte[] bytes = StringUtil.HexString2Bytes(clientHellow);
            byte[] bytes1 = StringUtil.HexString2Bytes(tlsMessage);
            for (int i = 10; i > 0; i--){


                if (bytes[0] == 0x16){
                    System.out.println("0x16");
                }
                connect.channel().writeAndFlush(Unpooled.copiedBuffer(bytes)); //发送byte


                if (bytes1[0] == 0x17){
                    System.out.println("0x17");
                }
                connect.channel().writeAndFlush(Unpooled.copiedBuffer(bytes1)); //发送byte
                Thread.sleep(1000);
            }

            // connect.channel().writeAndFlush(clientHellow);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }
    }
}
