package com.watchdata.common.netty.business.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.watchdata.common.netty.bean.BaseTls;
import com.watchdata.common.netty.business.TlsAnswer;
import com.watchdata.common.netty.client.AbstractTlsClient;
import com.watchdata.common.netty.config.TlsServerConfig;
import com.watchdata.server.scanner.Invoker;
import com.watchdata.server.scanner.InvokerHoler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhibin.wang
 * @desc 服务应答
 **/
@Slf4j
@Component
public class ServerTlsAnswerImpl implements TlsAnswer {

    private final AbstractTlsClient tlsClient;

    private final ExecutorService executorService;

    private final TlsServerConfig tlsServerConfig;

    @Autowired
    public ServerTlsAnswerImpl(AbstractTlsClient tlsClient, TlsServerConfig tlsServerConfig) {
        this.tlsClient = tlsClient;
        this.tlsServerConfig = tlsServerConfig;
        executorService = new ThreadPoolExecutor(tlsServerConfig.getThreadPoolNum()
                ,tlsServerConfig.getThreadPoolNum(),0L, TimeUnit.MILLISECONDS
                ,new LinkedBlockingDeque<>(100)
                ,new ThreadFactoryBuilder().setDaemon(false).setNameFormat("tls-server-business-%d").build());
    }

    @Override
    public void answer(BaseTls baseTls) {

        executorService.execute(() -> {
            Invoker invoker = InvokerHoler.getInvoker(baseTls.getType());
            invoker.invoker(baseTls);
        });

    }
}
