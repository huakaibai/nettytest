package common;

import common.Util.StringUtil;

import java.util.Arrays;

/**
 * @author zhibin.wang
 * @create 2018-12-19 17:26
 * @desc
 **/
public class TlsMessage {


    private byte[] head = new byte[3];

    private byte[] length = new byte[2];

    private byte[] data;

    public byte[] getHead() {
        return head;
    }

    public void setHead(byte[] head) {
        this.head = head;
    }

    public byte[] getLength() {
        return length;
    }

    public void setLength(byte[] length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TlsMessage{" +
                "head=" + StringUtil.byte2hex(head) +
                ", length=" +  StringUtil.byte2hex(length) +
                ", data=" +  StringUtil.byte2hex(data) +
                '}';
    }
}
