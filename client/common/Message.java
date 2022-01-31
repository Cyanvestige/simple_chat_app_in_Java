package common;

import java.io.Serializable;

// A message contains the sender and the receiver, the content and type of a message(string or file)
public class Message implements Serializable {
    /*
        serialVersionoUID is a universal version identifier for a Serializable class. 
        Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object
    */
    private static final long serialVersionUID = 1L;
    private String sender;
    private String receiver;
    private String content;
    private String sendTime;
    private String mesType;
    private byte[] fileBytes;
    private int fileLen = 0;
    private String dest; // the target directory


    private String src; // directory of the the source file

    public String getMesType() { return mesType; }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public byte[] getFileBytes() { return fileBytes; }

    public void setFileBytes(byte[] fileBytes) { this.fileBytes = fileBytes; }

    public String getDest() { return dest; }

    public void setDest(String dest) { this.dest = dest; }

    public String getSrc() { return src; }

    public void setSrc(String src) { this.src = src; }

}
