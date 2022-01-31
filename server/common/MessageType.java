package common;
// Use string constants to indicate MessageType
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";
    String MESSAGE_LOGIN_FAIL = "2";
    String MESSAGE_DIRECT_MES = "3"; 
    String MESSAGE_GET_ONLINE_FRIEND = "4";
    String MESSAGE_RET_ONLINE_FRIEND = "5";
    String MESSAGE_CLIENT_EXIT = "6";
    String MESSAGE_TO_ALL_MES = "7";
    String MESSAGE_FILE_MES = "8";
    String MESSAGE_USER_ERROR = "9";
    String MESSAGE_FILE_ERROR = "10";
}
