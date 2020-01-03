package com.watchdata.server;

import com.watchdata.server.interfaces.TlsInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author zhibin.wang
 * @create 2018-12-24 15:23
 * @desc
 **/
public class StartServer {

    public static void  main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        if (applicationContext != null)
            System.out.println("not null");
        Server bean = applicationContext.getBean(Server.class);
        bean.start();
    }
}
