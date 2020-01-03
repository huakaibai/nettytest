package com.watchdata.common.netty.client;

import com.watchdata.common.netty.bean.BaseTls;
import com.watchdata.common.netty.bean.RpcResponseState;

/**
 * @author zhibin.wang
 * @desc
 **/
public abstract class AbstractTlsClient {


    /**
     * 这个方法只有发送状态，不等待应答结果
     * @param key
     * @param message
     * @return
     */
    public abstract RpcResponseState send(String key,BaseTls message);

    /**
     * 这个方式等待应答结果
     * @param key
     * @param message
     * @return
     */
    public abstract BaseTls request(String key, BaseTls message);
}
