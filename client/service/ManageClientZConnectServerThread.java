package service;

import java.util.HashMap;

public class ManageClientZConnectServerThread {
    //we put multiple Threads to a HashMap, key is the userid, value is the Thread.
    private static HashMap<String, ClientConnectServerThread> map= new HashMap<>();

    //put a thread to the collection
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread){
        map.put(userId, clientConnectServerThread);
    }
    //use userId to get corresponding thread
    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        return map.get(userId);
    }


}
