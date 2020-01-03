package com.watchdata.common.netty.bean;

import lombok.Data;

/**
 * @author zhibin.wang
 * @desc
 **/
@Data
public class TlsSession {

    private String  clientHello;
    private String serverHello;
    private String clientCertificate;
    private String serverCertificate;
    private String serverkeyExchange;
    private String certificateRequest;
    private String serverHelloDone;
    private String clientkeyExchange;
    private String certificateverify;
    private String clientCipher;
    private String clientFinished;
    private String serverCipher;
    private String serverFinished;

}
