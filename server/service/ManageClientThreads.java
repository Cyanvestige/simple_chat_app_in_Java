package service;
import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThreads {
    // use a HashMap to manage all threads which are online users 
    public static HashMap<String, ServerConnectClientThread> map = new HashMap<>();

    public static HashMap<String, ServerConnectClientThread> getMap(){
        return map;
    }

    // add a user's thread to the map
    public static void addServerConnectClientThread(String userID, ServerConnectClientThread serverConnectClientThread){
        map.put(userID, serverConnectClientThread);
    }

    public static ServerConnectClientThread getServerConnectClientThread(String userId){
        return map.get(userId);
    }

    public static void removeServerConnectClientThread(String userId){
        map.remove(userId);
    }

    // method for showing the list of online users
    public static String getOnlineUser(){
        Iterator<String> iterator = map.keySet().iterator();
        String onlineUserList = "";
        while(iterator.hasNext()){
            onlineUserList += iterator.next() + " ";
        }
        return onlineUserList;
    }
}
