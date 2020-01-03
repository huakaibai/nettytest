package com.watchdata.common.netty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhibin.wang
 * @desc
 **/
@ConfigurationProperties(prefix = "tls.server")
@Component
@Data
public class TlsServerConfig {


    private int port;

    private String ip;

    /**
     * 心态检测时间(ms)
     */
    private long checkTime;

    private final int threadPoolNum = Runtime.getRuntime().availableProcessors() * 2;
}
