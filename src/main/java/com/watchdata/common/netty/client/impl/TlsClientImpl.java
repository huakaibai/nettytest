package com.watchdata.common.netty.client.impl;

import com.watchdata.common.netty.bean.BaseTls;
import com.watchdata.common.netty.bean.RpcResponseState;
import com.watchdata.common.netty.bean.SocketManager;
import com.watchdata.common.netty.client.AbstractTlsClient;
import com.watchdata.common.netty.config.TlsConfig;
import org.springframework.stereotype.Component;

/**
 * @author zhibin.wang
 * @desc tls客户端实现类
 **/
@Component
public class TlsClientImpl extends AbstractTlsClient {


    @Override
    public RpcResponseState send(String key, BaseTls message) {
        // 考虑tlssession怎么管理

        return  SocketManager.getInstance().send(key,message);
    }

    @Override
    public BaseTls request(String key, BaseTls message) {
        // 封装数据发送
        return request0(key, message);

    }

    public BaseTls request0(String key, BaseTls message){
        return SocketManager.getInstance().request(key, message, TlsConfig.timeout);
    }



}
