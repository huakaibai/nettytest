package com.watchdata.common;

import com.watchdata.common.Util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zhibin.wang
 * @create 2018-12-19 15:03
 * @desc
 **/
public class TlsDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        /**
         * *****************************************************************
         * |protol|major version|minor version|length|type| 所有TLS数据都是这样的 *
         *  16          03         03                   01  clientHello         *
         *  16          03         03                   10  clientKeyExchange   *
         *  16          03         03           0050        clientFinshed       *
         *  14          03         03                       ChangerCipherSpec   *
         *  15          03         03                       alert               *
         *  17          03         03                       dataExchange        *
         *********************************************************************
         *
         */
        System.out.println(Thread.currentThread().getName()+"decoder");
        // 可读长度必须大于基本长度
        if (in.readableBytes() <= 5) {
            return;
        }

        //防止字节流攻击
        if (in.readableBytes() > 2048) {
            in.skipBytes(in.readableBytes());
        }


        byte[] head = new byte[3];
        //记录包头开始的index
        int beginReader;
        while (true) {
            beginReader = in.readerIndex();

            in.markReaderIndex();

            in.readBytes(head);

            //是正确的TLS头
            if (isHead(head)) {
                break;
            }
            in.resetReaderIndex(); //跳转到开头
            in.readByte();//略过一个字节
            if (in.readableBytes() <= 5) {

                return;
            }

        }

/*        in.markReaderIndex();
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);
        System.out.println(StringUtil.byte2hex(req));
        System.out.println(req.length);
        in.resetReaderIndex();*/

        byte[] lengthByte = new byte[2];
        in.readBytes(lengthByte);
        String hexLength = StringUtil.byte2hex(lengthByte);
        Integer intLength =  Integer.valueOf(hexLength, 16);
        if (in.readableBytes() < intLength) {
            in.readerIndex(beginReader);
            return;
        }

        // 是否是0x16开头
        if (head[0] == 0x16) {
            byte type = in.readByte();
            //封装clientHello
            if (isClientHello(head, type)) {
                ClietnHello clietnHello = new ClietnHello();
                clietnHello.setHead(head);
                clietnHello.setLength(lengthByte);
                clietnHello.setHanddshakeType(type);

                byte[] length2Byte = new byte[3];
                in.readBytes(length2Byte);
                clietnHello.setLength2(length2Byte);

       /*    String hexLength2 = StringUtil.byte2hex(length2Byte);
     if(in.readableBytes() < Integer.valueOf(hexLength2,16)){
            in.resetReaderIndex();
            return;
        }
        */

                clietnHello.setMajor_version(in.readByte());
                clietnHello.setMinor_version(in.readByte());

                byte[] randomByte = new byte[32];
                in.readBytes(randomByte);
                clietnHello.setRandomValue(randomByte);


                clietnHello.setSessionIdLength(in.readByte());

                byte[] cipherSuiteLength = new byte[2];
                in.readBytes(cipherSuiteLength);
                clietnHello.setCipherSuiteLength(cipherSuiteLength);

                String hexCiperSuiteLength = StringUtil.byte2hex(cipherSuiteLength);
                byte[] ciperSuite = new byte[Integer.valueOf(hexCiperSuiteLength, 16)];
                in.readBytes(ciperSuite);
                clietnHello.setCipherSuite(ciperSuite);

                byte[] compressionByte = new byte[9];
                in.readBytes(compressionByte);
                clietnHello.setCompression(compressionByte);

                BaseTls baseTls = new BaseTls();
                baseTls.setType(ConstantValue.CLIENT_HELLO);
                baseTls.setObject(clietnHello);
                out.add(baseTls);

            }
        } else if (isDataExchange(head)) {
            TlsMessage tlsMessage = new TlsMessage();
            tlsMessage.setHead(head);
            tlsMessage.setLength(lengthByte);
            byte[] data = new byte[intLength];
            in.readBytes(data);
            tlsMessage.setData(data);

            BaseTls baseTls = new BaseTls();
            baseTls.setType(ConstantValue.TLS_MESSAGE);
            baseTls.setObject(tlsMessage);
            out.add(baseTls);
        }


    }

    private boolean isHead(byte[] head) {
        if (!(head[0] == 0x17 || head[0] == 0x16 || head[0] == 0x15 || head[0] == 0x14)) {
            return false;
        }
        if (head[1] != 0x03)
            return false;
        if (head[2] != 0x03)
            return false;

        return true;
    }

    private boolean isClientHello(byte[] head, byte type) {
        if (head[0] == 0x16 && type == 0x01)
            return true;
        return false;
    }

    private boolean isClientKeyExchange(byte[] head, byte type) {
        if (head[0] == 0x16 && type == 0x10)
            return true;
        return false;
    }

    private boolean isClientFinshed(byte[] head, byte[] length) {
        if (head[0] == 0x16 && length[1] == 0x50) {
            return true;
        }

        return false;
    }


    private boolean isChangerCipherSpec(byte[] head) {
        if (head[0] == 0x14)
            return true;
        return false;
    }

    private boolean isAlert(byte[] head) {
        if (head[0] == 0x15)
            return true;
        return false;
    }


    private boolean isDataExchange(byte[] head) {
        if (head[0] == 0x17)
            return true;

        return false;
    }

}
