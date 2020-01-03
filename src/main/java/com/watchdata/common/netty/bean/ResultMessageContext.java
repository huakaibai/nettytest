package com.watchdata.common.netty.bean;

import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhibin.wang
 * @desc 包含返回消息的包装类
 **/
@Data
public class ResultMessageContext {


    /**
     *  远程消息Key
     */
    private String key ;


    /**
     * 远程应答消息
     */
    private volatile transient BaseTls baseTls;

    private volatile  transient TlsSession tlsSession;
    /**
     * 等待时间
     */
    private long milliseconds;

    private Condition condition;

    private Lock lock;





    public ResultMessageContext(long milliseconds,String key){
        this.milliseconds = milliseconds;
        lock = new ReentrantLock(true);
        condition = lock.newCondition();
        this.key = key;
    }


    public void clear(){
        setBaseTls(null);

    }

    public void await(){
        await(milliseconds);
    }

    public void await(long timeOut){
        try {
            lock.lock();
            condition.await(timeOut, TimeUnit.MILLISECONDS);

        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    public void signal(){
        try {
            lock.lock();
            condition.signal();
        }finally {
            lock.unlock();
        }
    }


    public synchronized BaseTls getBaseTls() {
        return baseTls;
    }

    public synchronized void setBaseTls(BaseTls baseTls) {
        this.baseTls = baseTls;
    }

}
