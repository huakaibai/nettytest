package com.watchdata.server.interfaces;

import com.watchdata.common.ClietnHello;
import com.watchdata.common.TlsMessage;
import com.watchdata.common.annotion.TlsTittle;
import com.watchdata.common.annotion.TlsType;
import com.watchdata.server.UseService;

/**
 * @author zhibin.wang
 * @create 2018-12-24 14:52
 * @desc
 **/
@TlsTittle
public interface TlsInterface {
    @TlsType(type = 1)
    void clientHello(UseService useService, ClietnHello clietnHello);
    @TlsType(type = 2)
    void TlsMessage(UseService useService, TlsMessage tlsMessage);
}
