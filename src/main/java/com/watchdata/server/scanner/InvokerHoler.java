package com.watchdata.server.scanner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhibin.wang
 * @create 2018-12-24 14:40
 * @desc
 **/
public class InvokerHoler {

    private static Map<Short,Invoker> invokers = new HashMap<Short, Invoker>();

    public static void  addInvoker(short type,Invoker invoker){
        invokers.put(type,invoker);
    }

    public static Invoker getInvoker(short type){
        Invoker invoker = invokers.get(type);
        if (invoker == null)
            return null;

        return  invoker;
    }
}
