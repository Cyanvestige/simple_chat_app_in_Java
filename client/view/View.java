package view;

import service.FileClientService;
import service.MessageClientService;
import service.UserClientService;
import utils.Utility;

import java.io.IOException;
import java.util.Scanner;

// user interface
public class View {
    public static void main(String[] args) throws IOException {
        new View().mainMenu();
    }
    //show menu
    private boolean run = true;//control menu on/off
    private String key = "";//input key
    private Scanner sc = new Scanner(System.in);
    private UserClientService userClientService = new UserClientService();//used for register/login
    private MessageClientService messageClientService = new MessageClientService();//service for direct message and group message between users
    private FileClientService fileClientService = new FileClientService();// service for sending files between users
    private void mainMenu() throws IOException {
        while(run){
            System.out.println("=====================Welcome to Chatter=======================");
            System.out.println("\t\t 1 Log in");
            System.out.println("\t\t 9 Exit");
            System.out.println("Please select an option");
            key = Utility.readString(1);

            //According to the user input, return different function;
            switch (key) {
                case "1":
                    System.out.println("Login system");
                    System.out.println("Please input you user ID:");
                    String userId = Utility.readString(50);
                    System.out.println("Please input your password:");
                    String pwd = Utility.readString(50);
                    //we need to authenticate the user on the server-side
                    //we need a class UserClientService to provide the register/login function
                    if(userClientService.checkUser(userId,pwd)){
                        System.out.println("================== Welcome back ( User " + userId + " ) ====================");
                        //second-layer menu
                        while(run) {
                            try {
                                Thread.sleep(500); 
                            } catch (InterruptedException e) {
                            }
                            System.out.println("\n===================== Menu ( User " + userId + " ) =======================");
                            System.out.println("\t\t 1 Display Online Users");
                            System.out.println("\t\t 2 Group Message");
                            System.out.println("\t\t 3 Direct Message");
                            System.out.println("\t\t 4 Send a File");
                            System.out.println("\t\t 9 Exit System");
                            System.out.println("Please select an option");
                            key = Utility.readString(1);
                            switch (key){
                                case "1":
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("What do you want to say to everyone?");
                                    String msContent = Utility.readString(100);
                                    messageClientService.sendMessageAll(msContent,userId);
                                    break;
                                case "3":
                                    System.out.println("Who do you want to send the message to?");
                                    String receiver = Utility.readString(50);
                                    System.out.println("Input your message");
                                    String mesContent = Utility.readString(100);
                                    messageClientService.sendMessageToOne(mesContent,userId,receiver);
                                    break;
                                case "4":
                                    System.out.println("Who do you want to send the file to?");
                                    String receiverId = Utility.readString(50);
                                    System.out.println("Input the file name you want to send");
                                    String fileName = Utility.readString(100);
                                    fileClientService.sendFileToOne(fileName,userId,receiverId);
                                    break;
                                case "9":
                                    userClientService.logOut();
                                    break;
                            }

                        }
                    }else{//authentication failed
                        System.out.println("==========Authentication failed==========");
                    }
                    break;
                case "9":
                    run = false;
                    break;
            }

        }
    }
}
