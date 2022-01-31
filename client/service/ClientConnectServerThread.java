package service;
import common.Message;
import common.MessageType;

import java.io.*;
import java.net.*;
public class ClientConnectServerThread extends Thread{
    //this thread has to have a Socket
    private Socket socket;

    //the constructor can accept a Socket object
    public ClientConnectServerThread(Socket socket){
        this.socket = socket;
    }
    public Socket getSocket(){
        return socket;
    }
    @Override
    public void run(){
        //the Thread needs to keep communicating with the server, so we use while(true)
        while(true){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message ms = (Message) ois.readObject();//if the server does not send message, it will keep waiting
                if(ms.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    String[] onlineFriendsList = ms.getContent().split(" ");
                    System.out.println("==============Who is Online?===============");
                    for(String friend : onlineFriendsList){
                        System.out.println("User: " + friend);
                    }
                } else if(ms.getMesType().equals(MessageType.MESSAGE_DIRECT_MES)){
                    System.out.println(ms.getSender() + " said \n\n\""+ ms.getContent() +"\"\n\n to you");
                } else if(ms.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    System.out.println(ms.getSender() + " said \n\n\"" + ms.getContent() +"\"\n\n to everyone" );
                }else if(ms.getMesType().equals(MessageType.MESSAGE_FILE_MES)){
                    System.out.println(ms.getSender() + " sent you a file \"" + ms.getSrc() +"\" to your directory " + ms.getDest());
                    // use
                    FileOutputStream fileOutputStream = new FileOutputStream(ms.getDest());
                    fileOutputStream.write(ms.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("File has been Saved!");
                }else if(ms.getMesType().equals(MessageType.MESSAGE_USER_ERROR)){
                    System.out.println(ms.getContent());
                }
                else{
                    System.out.println("Other type of Message, abort...");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
