package service;

import common.Message;
import common.MessageType;

import java.io.*;
import java.net.Socket;
import java.nio.file.*;

// this class contains methods for functions that are related to file sending on the client side 
public class FileClientService {

    /**
     * @param src source file
     * @param dest the destination directory(receiver's directory)
     * @param senderId
     * @param receiverId
     */
    public void sendFileToOne(String fileName, String senderId, String receiverId) throws IOException {
        // read the "src" file
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setReceiver(receiverId);
        Path currentRelativePath = Paths.get("");
        String currPath = currentRelativePath.toAbsolutePath().toString();
        System.out.println(currPath);
        message.setSrc(currPath+"/"+senderId+"/"+fileName);
        if(receiverId == senderId){
            System.out.println("You cannot send a file to yourself !");
            return;
        }
        else{
            if(receiverId.equals("Alice")){
                message.setDest(currPath+"/Alice/"+fileName);
            }else if(receiverId.equals("Bob")){
                message.setDest(currPath+"/Bob/"+fileName);
                System.out.println(message.getReceiver());
            }else
                message.setDest(currPath+"/Cindy/"+fileName);
        }

        // read the file to FileInputStream
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int)new File(message.getSrc()).length()];
        try{
            System.out.println(message.getSrc());
            System.out.println(message.getDest());
            // create a new instance of FileInputStream with the "src" file and load the file to "fileBytes" array
            fileInputStream = new FileInputStream(message.getSrc());
            fileInputStream.read(fileBytes);
            // set the fileBytes for message
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }finally {
            // close the fileInputStream
            if(fileInputStream != null){
                try{
                    fileInputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }


        // sender's socket
        Socket sendersSocket = ManageClientZConnectServerThread.getClientConnectServerThread(senderId).getSocket();
        // the outputstream for object of sender's socket
        ObjectOutputStream oos = new ObjectOutputStream(sendersSocket.getOutputStream());
        oos.writeObject(message);
        //inform the user
        System.out.println("\n" + "You sent the file \"" + fileName + "\" to \"" + receiverId + "\"");
    }

}

