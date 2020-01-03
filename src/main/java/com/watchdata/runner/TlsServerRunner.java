package com.watchdata.runner;

import com.watchdata.client.boot.netty.NettyClientTlsInitializer;
import com.watchdata.common.netty.config.TlsClientConfig;
import com.watchdata.server.boot.NettyTlsInitializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author zhibin.wang
 * @desc Tls 启动服务
 **/
@Component
public class TlsServerRunner implements ApplicationRunner {

    @Autowired
    private NettyTlsInitializer nettyTlsInitializer;

    @Autowired
    private NettyClientTlsInitializer clientTlsInitializer;

    @Autowired
    private TlsClientConfig tlsClientConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettyTlsInitializer.init();
        clientTlsInitializer.init();
        clientTlsInitializer.connet(new InetSocketAddress(tlsClientConfig.getIp(),tlsClientConfig.getPort()));
    }


}
