package service;

import common.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;


public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId){
        this.socket = socket;
        this.userId = userId;//userId of the connected client
    }

    public Socket getSocket(){
        return socket;
    }
    @Override
    public void run(){
        while(true){
            System.out.println("Server and Client " + userId + " are communicating");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //if the server receives the request of asking for online friends list
                if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("\"" + message.getSender() + "\" asks for online friends list");
                    //return a message to the client
                    Message respondOnlineFriendList = new Message();
                    respondOnlineFriendList.setContent(ManageClientThreads.getOnlineUser());
                    respondOnlineFriendList.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    oos.writeObject(respondOnlineFriendList);
                }
                // if the server receives a message of logging out, we should tell the administrator who logged out on the server side"
                else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    System.out.println(message.getSender() + " Logged out.");
                    ManageClientThreads.removeServerConnectClientThread(message.getSender());
                    socket.close();
                    break;
                }

                // if we receivce a request of sending a direct message
                else if(message.getMesType().equals(MessageType.MESSAGE_DIRECT_MES)){
                    // if the message is sent to someone not online or a valid user
                    if(!ManageClientThreads.getMap().containsKey(message.getReceiver())){
                        // prepare the outputstream and create a message with type "error" telling the sender he is sending to someone invalid
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        Message respondError = new Message();
                        respondError.setMesType(MessageType.MESSAGE_USER_ERROR);
                        respondError.setContent("\"" + message.getReceiver() + "\" is not online or does not exist!");
                        oos.writeObject(respondError);
                    }
                    else{
                    // get receiver's socket and send the message to him
                        Socket receiversSocket = ManageClientThreads.getServerConnectClientThread(message.getReceiver()).getSocket();
                        ObjectOutputStream oos = new ObjectOutputStream(receiversSocket.getOutputStream());
                        System.out.println("Message sent to \"" + message.getReceiver()+"\"");
                        oos.writeObject(message);
                    }
                }
                
                // if the server receives a request of sending a group message
                else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    // iterate all client's threads and send the message to everyone except the sender himself
                    HashMap<String, ServerConnectClientThread> map = ManageClientThreads.getMap();
                    Iterator<String> iterator = map.keySet().iterator();
                    while(iterator.hasNext()){
                        String onlineUserId = iterator.next();
                        if(onlineUserId.equals(message.getSender()))continue;
                        ObjectOutputStream oos = new ObjectOutputStream(map.get(onlineUserId).getSocket().getOutputStream());
                        oos.writeObject(message);
                    }
                }
                // if the server receives a request of transferring a file
                else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MES)){
                    if(!ManageClientThreads.getMap().containsKey(message.getReceiver())){
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        Message respondError = new Message();
                        respondError.setMesType(MessageType.MESSAGE_USER_ERROR);
                        respondError.setContent("\"" + message.getReceiver() + "\" is not online or does not exist!");
                        oos.writeObject(respondError);
                    }
                    else{
                    // get the thread of receiver
                        ServerConnectClientThread serverConnectClientThread = ManageClientThreads.getServerConnectClientThread(message.getReceiver());
                        // get receiver's socket
                        Socket receiversSocket = serverConnectClientThread.getSocket();
                        // get the outputstream of the receiversSocket
                        ObjectOutputStream oos = new ObjectOutputStream(receiversSocket.getOutputStream());
                        oos.writeObject(message);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
