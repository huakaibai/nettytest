package com.watchdata.server.interfaces;

import com.watchdata.common.ClietnHello;
import com.watchdata.common.TlsMessage;
import com.watchdata.common.netty.bean.BaseTls;
import com.watchdata.common.netty.bean.ResultMessageContext;
import com.watchdata.common.netty.bean.ResultMessageManage;
import com.watchdata.server.UseService;
import org.springframework.stereotype.Component;

/**
 * @author zhibin.wang
 * @create 2018-12-24 15:12
 * @desc
 **/
@Component
public class TlsInterfaceImpl implements  TlsInterface {


    @Override
    public void clientHello(UseService useService, ClietnHello clietnHello) {
        useService.messageProcess(clietnHello.toString());
    }

    @Override
    public void TlsMessage(UseService useService, TlsMessage tlsMessage, String key) {
        // 这里进行通知发送消息成功
        ResultMessageContext context = ResultMessageManage.getInstance().getContext(key);
        BaseTls baseTls = new BaseTls();
        baseTls.setObject(tlsMessage);
        // 可以用枚举
        baseTls.setType((short) 2);
        context.setBaseTls(baseTls);
        context.signal();
    }

}



