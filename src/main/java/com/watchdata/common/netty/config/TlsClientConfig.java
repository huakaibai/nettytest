package com.watchdata.common.netty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhibin.wang
 * @desc 客户端配置参数
 **/

@ConfigurationProperties("tls.client")
@Component
@Data
public class TlsClientConfig {

    /**
     * 客户端重新连接次数
     */
    private int reConnectNum;

    /**
     * 客户端等待重新连接次数（ms）
     */
    private int waitConnectTime;


    private String ip;

    private int port;
}
