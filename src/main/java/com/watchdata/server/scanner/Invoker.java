package com.watchdata.server.scanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhibin.wang
 * @create 2018-12-24 14:35
 * @desc
 **/
public class Invoker {

    private Object object;

    private Method method;

    public static Invoker valueOf(Object object,Method method){
        Invoker invoker = new Invoker();
        invoker.setObject(object);
        invoker.setMethod(method);

        return invoker;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }


    public Object invoker(Object... paramValues){
        try {
           return  method.invoke(object,paramValues);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
