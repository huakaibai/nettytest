package com.watchdata.server.scanner;

import com.watchdata.common.annotion.TlsTittle;
import com.watchdata.common.annotion.TlsType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zhibin.wang
 * @create 2018-12-24 14:18
 * @desc
 **/
@Component
public class HandlerScaner implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {

        Class<?> clazz = o.getClass();


        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces != null && interfaces.length > 0){
            for (Class<?> interFace: interfaces) {
                TlsTittle tlsTittle = interFace.getAnnotation(TlsTittle.class);
                if (tlsTittle == null){
                    continue;
                }
                Method[] methods = interFace.getMethods();
                if (methods != null && methods.length > 0){
                    for (Method method:methods
                         ) {
                        TlsType tlsType = method.getAnnotation(TlsType.class);
                        if (tlsType == null)
                            continue;
                        final short type = tlsType.type();
                        if (InvokerHoler.getInvoker(type) == null){
                            Invoker invoker = Invoker.valueOf(o, method);
                            InvokerHoler.addInvoker(type,invoker);
                        }

                    }
                }

            }
        }

        return o;
    }
}
