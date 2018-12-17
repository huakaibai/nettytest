package server;

/**
 * @author zhibin.wang
 * @create 2018-12-17 13:55
 * @desc
 **/
public class UseService {




    private int i =0;

    public void messageProcess(String msg){

        System.out.println(i++);
        System.out.println("收到消息"+msg);
    }
}
