package service;

import common.Message;
import common.User;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.*;

// this class contains methods for functions that are related to the user on the client side 
public class UserClientService {
    private User u = new User();// we may need user's information somewhere
    private Socket socket;
    //send userId to server to authenticate the user
    public boolean checkUser(String userId, String pwd)  {
        boolean b = false;
        u.setUserId(userId);
        u.setPasswd(pwd);
        //connect to the server

        try {
            socket = new Socket(InetAddress.getLocalHost(), 5000);
            //get an ObjectOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            //read the Message object from the server
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message)ois.readObject();

            if(ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
                // start a Thread to keep communicating with server-> create a class ClientConnectServerThread
                //wait...
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                //start the client Thread
                clientConnectServerThread.start();
                // for sustainability we prefer putting Threads to a collection
                ManageClientZConnectServerThread.addClientConnectServerThread(userId,clientConnectServerThread);
                b = true;
            }else{
                //if login failed we can not start the thread with the server
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    //ask for the list of online friends from the server

    public void onlineFriendList(){

        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());
        try {
            Socket currClientSocket = ManageClientZConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket();
            ObjectOutputStream getOnlineFriendOutStream = new ObjectOutputStream(currClientSocket.getOutputStream());
            getOnlineFriendOutStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Log out
    public void logOut(){
        Message logOutMessage = new Message();
        logOutMessage.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        logOutMessage.setSender(u.getUserId());
        try{
            ClientConnectServerThread currClientThread = ManageClientZConnectServerThread.getClientConnectServerThread(u.getUserId());
            Socket currClientSocket = currClientThread.getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(currClientSocket.getOutputStream());
            oos.writeObject(logOutMessage);
            System.exit(0);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    //direct message

}
