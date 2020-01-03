package com.watchdata.common.netty.bean;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;

/**
 * @author zhibin.wang
 * @desc 用于记录所有的有效连接 单列模式
 **/
public class SocketManager {

    private ChannelGroup channels;

    private static SocketManager manager = null;

  //  private ScheduledExecutorService executorService;

    private SocketManager(){
        channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
      /*  executorService = Executors.newSingleThreadScheduledExecutor();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executorService.shutdown();
            try {
                executorService.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException ignored) {
            }
        }));*/

    }

    public static SocketManager getInstance(){
        if (manager == null){
            synchronized (SocketManager.class){
                if (manager == null){
                    manager = new SocketManager();
                }
            }
        }
        return manager;
    }


    public boolean noConnect(SocketAddress key){
        for (Channel channel : channels) {
            if (channel.remoteAddress().toString().equals(key.toString())){
                return false;
            }
        }
        return true;
    }

    public void addChannel(Channel channel){
        channels.add(channel);
    }


    public void  removeChannel(Channel channel){
        channels.remove(channel);
    }


    private Channel getChannel(String key){
        for (Channel channel : channels) {
            String val = channel.remoteAddress().toString();
            if (key.equals(val)){
                return channel;
            }
        }
        return null;


    }

    /**
     *
     * @param key 客户端ip地址
     * @param baseTls
     * @return
     */
    public RpcResponseState send(String key, BaseTls baseTls){
        Channel channel = getChannel(key);
        ChannelFuture future = channel.writeAndFlush(baseTls).syncUninterruptibly();
        return future.isSuccess() ? RpcResponseState.success : RpcResponseState.fail;
    }

    /**
     *
     * @param key 客户端ip地址
     * @param requestTls 请求Tls
     * @param timeOut 应答等待时间
     * @return
     */
    public BaseTls request(String key,BaseTls requestTls,long timeOut){
        Channel channel = getChannel(key);
        channel.writeAndFlush(requestTls);

        ResultMessageContext context = ResultMessageManage.getInstance().getContext(key);
        if (timeOut < 1){
            context.await();
        }else {
            context.await(timeOut);
        }
        // 被唤醒，读取返回数据
        BaseTls responseTls = context.getBaseTls();
        context.clear();
        return requestTls;

    }

}
