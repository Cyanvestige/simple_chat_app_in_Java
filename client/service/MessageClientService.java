package service;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

// this class contains methods for functions that are related to message on the client side 
public class MessageClientService {
    public void sendMessageToOne(String content, String senderId, String receiverId){
        Message mes = new Message();
        mes.setSender(senderId);
        mes.setReceiver(receiverId);
        mes.setContent(content);
        mes.setSendTime(new Date().toString());
        mes.setMesType(MessageType.MESSAGE_DIRECT_MES);
        System.out.println( "You said \n\n\"" + mes.getContent() + "\"\n\n to " + mes.getReceiver());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientZConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(mes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageAll(String content, String senderId){
        Message mes = new Message();
        mes.setSender(senderId);
        mes.setContent(content);
        mes.setSendTime(new Date().toString());
        mes.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        System.out.println(senderId + " says \n\n\"" + content + "\"\n\n to everyone.");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientZConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(mes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendFile(String receiver){
        byte[] buf = new byte[1024];
    }

}
