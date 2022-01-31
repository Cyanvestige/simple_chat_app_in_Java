package common;
//user's information

import java.io.Serializable;

//a User's information
public class User implements Serializable {
    /*
        serialVersionoUID is a universal version identifier for a Serializable class. 
        Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object
    */
    private static final long serialVersionUID = 1L;
    private String userId;
    private String passwd;

    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
