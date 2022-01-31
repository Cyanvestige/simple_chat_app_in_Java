package service;
import common.*;
import service.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;

public class Server {
    // server socket instance is initialized to be null
    private ServerSocket serverSocket = null;
    
    // we are using this HashMap to simulate a use database. 
    // we can also use ConcurrentHashMap for better security
    private static HashMap<String, User>  validUsers = new HashMap<>();
    
    // define some users to simulate database 
    static{
        validUsers.put("Alice", new User("Alice","111111"));
        validUsers.put("Bob", new User("Bob","222222"));
        validUsers.put("Cindy", new User("Cindy","333333"));
    }

    // check if the user information is valid(authentication)
    private boolean checkUser(String userId, String passwd){
        User user = validUsers.get(userId);
        if(user == null) return false;
        if(!user.getPasswd().equals(passwd)) return false;
        return true;
    }

    public Server(){
        try {
            // listening port 5000
            System.out.println("Listening at 5000");
            serverSocket = new ServerSocket(5000);
            while(true){//keep listening
                // ready to accept connections from users
                Socket socket = serverSocket.accept();

                // initialize instances for ObjectInputStream and ObjectOutputStream to receive User object
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                
                // receive the user object(we need to cast the Object to User)
                User u = (User)ois.readObject();

                // create a Message object, ready to respond the client
                Message message = new Message();
                
                // if the user is a valid user in our database 
                if(checkUser(u.getUserId(), u.getPasswd())){
                    // server tells the user "login succeeded"
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //respond the message to the client
                    oos.writeObject(message);
                    //create a thread, run it and manage the thread on the server side
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket,u.getUserId());
                    serverConnectClientThread.start();
                    ManageClientThreads.addServerConnectClientThread(u.getUserId(),serverConnectClientThread);

                }
                // authentication failed
                else{
                    // server tells the user "you failed"
                    System.out.println("Authentication Failed");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //if server exits while loop, we do not need to listen to connection anymore so we close the ServerSocket
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






}
