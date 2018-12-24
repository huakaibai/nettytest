package com.watchdata.server.interfaces;

import com.watchdata.common.ClietnHello;
import com.watchdata.common.TlsMessage;
import org.springframework.stereotype.Component;
import com.watchdata.server.UseService;

/**
 * @author zhibin.wang
 * @create 2018-12-24 15:12
 * @desc
 **/
@Component
public class TlsInterfaceImpl implements  TlsInterface {


    public void clientHello(UseService useService, ClietnHello clietnHello) {
        useService.messageProcess(clietnHello.toString());
    }


    public void TlsMessage(UseService useService, TlsMessage tlsMessage) {
        useService.messageProcess(tlsMessage.toString());
    }
}
