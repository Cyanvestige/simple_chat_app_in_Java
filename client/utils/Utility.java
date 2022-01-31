package utils;
import java.io.*;
import java.util.*;

// methods for convenient user input
public class Utility {
    private static Scanner scanner = new Scanner(System.in);
    
    private static String readKeyBoard(int limit, boolean allowEmpty){
        String res = "";
        while(scanner.hasNextLine() ){
            res = scanner.nextLine();
            if(res.isEmpty()){
                if(allowEmpty)break;
                else {
                    System.out.println("Please input the required data");
                    continue;
                }
            }
            else if(res.length() > limit){
                System.out.println("You can not input more than " + limit + " digits.");
                continue;
            }
            else break;
        }
        return res;
    }

    public static String readString(int limit){
        String str = readKeyBoard(limit, false);
        return str;
    }

    

}
