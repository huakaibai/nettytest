package common;

import common.Util.StringUtil;

import java.util.Arrays;

/**
 * @author zhibin.wang
 * @create 2018-12-19 14:29
 * @desc
 **/
public class ClietnHello {


    private byte[]  head = new byte[3];



    private byte[] length = new byte[2];

    private byte handdshakeType;
    private byte[] length2 = new byte[3];

    private byte major_version;
    private byte minor_version;
    private byte[] randomValue = new byte[32];
    private byte sessionIdLength;
    private byte[] cipherSuiteLength = new byte[2];
    private byte[] cipherSuite;
    private byte[] compression = new byte[9];


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

    public byte getHanddshakeType() {
        return handdshakeType;
    }

    public void setHanddshakeType(byte handdshakeType) {
        this.handdshakeType = handdshakeType;
    }

    public byte[] getLength2() {
        return length2;
    }

    public void setLength2(byte[] length2) {
        this.length2 = length2;
    }

    public byte getMajor_version() {
        return major_version;
    }

    public void setMajor_version(byte major_version) {
        this.major_version = major_version;
    }

    public byte getMinor_version() {
        return minor_version;
    }

    public void setMinor_version(byte minor_version) {
        this.minor_version = minor_version;
    }

    public byte[] getRandomValue() {
        return randomValue;
    }

    public void setRandomValue(byte[] randomValue) {
        this.randomValue = randomValue;
    }

    public byte getSessionIdLength() {
        return sessionIdLength;
    }

    public void setSessionIdLength(byte sessionIdLength) {
        this.sessionIdLength = sessionIdLength;
    }

    public byte[] getCipherSuiteLength() {
        return cipherSuiteLength;
    }

    public void setCipherSuiteLength(byte[] cipherSuiteLength) {
        this.cipherSuiteLength = cipherSuiteLength;
    }

    public byte[] getCipherSuite() {
        return cipherSuite;
    }

    public void setCipherSuite(byte[] cipherSuite) {
        this.cipherSuite = cipherSuite;
    }

    public byte[] getCompression() {
        return compression;
    }

    public void setCompression(byte[] compression) {
        this.compression = compression;
    }

    @Override
    public String toString() {
        return "ClietnHello{" +
                "head=" +  StringUtil.byte2hex(head) +

                ", length=" + StringUtil.byte2hex(length) +
                ", handdshakeType=" + handdshakeType +
                ", length2=" +  StringUtil.byte2hex(length2) +
                ", major_version=" + major_version +
                ", minor_version=" + minor_version +
                ", randomValue=" + StringUtil.byte2hex(randomValue) +
                ", sessionIdLength=" + sessionIdLength +
                ", cipherSuiteLength=" +StringUtil.byte2hex(cipherSuiteLength) +
                ", cipherSuite=" + StringUtil.byte2hex(cipherSuite) +
                ", compression=" + StringUtil.byte2hex(compression) +
                '}';
    }
}
