package com.watchdata.client.boot.netty;

import com.watchdata.common.netty.bean.SocketManager;
import com.watchdata.common.netty.config.TlsClientConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zhibin.wang
 * @desc tls客户端启动初始化接口实现类
 **/
@Component
@Slf4j
public class NettyClientTlsInitializerImpl implements  NettyClientTlsInitializer, DisposableBean {

    private EventLoopGroup workerGroup;


    private final TlsClientConfig tlsClientConfig;


    private final NettyTlsClientChannelInitializer clientChannelInitializer;

    private static  NettyClientTlsInitializerImpl INSTANCE;

    @Autowired
    public NettyClientTlsInitializerImpl(TlsClientConfig tlsClientConfig, NettyTlsClientChannelInitializer clientChannelInitializer) {

        this.tlsClientConfig = tlsClientConfig;
        this.clientChannelInitializer = clientChannelInitializer;
        INSTANCE = this;
    }

    @Override
    public void init() {
        workerGroup = new NioEventLoopGroup();
        Optional<Future> future = connet(new InetSocketAddress(tlsClientConfig.getIp(), tlsClientConfig.getPort()));
        if (future.isPresent()){
            try {
                future.get().get(10, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException |TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Future> connet(SocketAddress socketAddress) {
        //
        for (int i = 0; i < tlsClientConfig.getReConnectNum();i++){
            // 如果客户端没有连接，则进行连接
            if (SocketManager.getInstance().noConnect(socketAddress)){
                try {
                    Bootstrap b = new Bootstrap();
                    b.group(workerGroup);
                    b.channel(NioSocketChannel.class);
                    b.option(ChannelOption.SO_KEEPALIVE,true);
                    b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000);
                    b.handler(clientChannelInitializer);
                    return Optional.of(b.connect(socketAddress).syncUninterruptibly());
                }catch (Exception e){
                    e.printStackTrace();
                    log.warn("connect socket ({}) fail,{}ms later try again",socketAddress.toString(),tlsClientConfig.getWaitConnectTime());
                    try {
                        Thread.sleep(tlsClientConfig.getWaitConnectTime());
                    }catch (InterruptedException e1){
                        log.error("sleep exception",e1);
                    }
                    continue;
                }
            }
            return Optional.empty();
        }

        // 忽略已经连接数据
        log.warn("Finally, netty connection fail , socket is {}", socketAddress);
        return Optional.empty();
    }


    public static void reConnect(SocketAddress socketAddress){
        Objects.requireNonNull(socketAddress,"socketAddress not NUll!");
        INSTANCE.connet(socketAddress);
    }

    @Override
    public void destroy() throws Exception {

        workerGroup.shutdownGracefully();
        log.info("Tls client shutdown");
    }
}
