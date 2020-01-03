package com.watchdata.client.boot.netty;

import java.net.SocketAddress;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * @author zhibin.wang
 * @create 2020-01-03 10:14
 * @desc tls客户端启动初始化接口
 **/
public interface NettyClientTlsInitializer {

    void init();


    /**
     *  客户端连接服务器地址
     * @param socketAddress
     * @return
     */
    Optional<Future> connet(SocketAddress socketAddress);
}
