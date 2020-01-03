package com.watchdata.common.netty.business;

import com.watchdata.common.netty.bean.BaseTls;

/**
 * @author zhibin.wang
 * @create 2020-01-02 16:42
 * @desc tls应答
 **/
public interface TlsAnswer {


    void answer(BaseTls baseTls);
}
