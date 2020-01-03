package com.watchdata.common.netty.bean;

import com.watchdata.common.netty.config.TlsConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhibin.wang
 * @desc 应答消息管理
 **/
public class ResultMessageManage {


    private  Map<String,ResultMessageContext> resultMap;

    private static ResultMessageManage  instance = null;


    public static ResultMessageManage getInstance(){
        if (instance == null){
            synchronized (ResultMessageManage.class){
                if (instance == null){
                    instance = new ResultMessageManage();
                }
            }
        }
        return instance;
    }

    private ResultMessageManage(){
        resultMap = new ConcurrentHashMap<>();
    }

    public void addContext(String key){

        if (!resultMap.containsKey(key)){

            ResultMessageContext resultMessageContext = new ResultMessageContext(TlsConfig.timeout, key);

            resultMap.put(key,resultMessageContext);
        }

    }

    public ResultMessageContext getContext(String key){

        ResultMessageContext resultMessageContext = resultMap.get(key);
        if (resultMessageContext == null){
            addContext(key);
        }
        return resultMessageContext;
    }

    public void removeConext(String key){
        resultMap.remove(key);
    }
}
