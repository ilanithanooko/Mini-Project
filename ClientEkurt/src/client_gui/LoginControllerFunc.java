package client_gui;

import java.util.HashMap;

import clientUtil.Utils;
import client.ClientUI;
import common.Action;
import common.Transaction;
import enums.RoleEnum;
import logic.User;

/**
 * This class handles the Item methods to be sent and received from the server for the relevant controllers
 */
public class LoginControllerFunc {
    /**
     * This method creates a new message for logging in a user with it username and password provided
     * @param username The username of the client
     * @param password The password of the client
     */
    public static void loginByUsernameAndPassword(String username, String password) {
        HashMap<String, String> args = new HashMap<>();
        args.put("username", username);
        args.put("password", password);
        Transaction msg = new Transaction(Action.LOGIN_USERNAME_PASSWORD, args);
        ClientUI.chat.accept(msg);
    }


    /**
     * This method creates a new message used to update the login status of an account in the GUI and the Current User
     * @param msg The message containing the answer regarding the log in status of the account from the server
     */
    
    public static void updateLoginStatus(Transaction msg) {
        String loginStatus = "";
        switch (msg.getResponse()) {
            case SUCCEED:
                loginStatus = "Login successfully";
                break;
            case INCORRECT_VALUES:
                loginStatus = "Username or password is incorrect";
                break;
            case ALREADY_LOGGED_IN:
                loginStatus = "User already logged-in";
                break;
        }
        RoleEnum currUserRole = null;
        if (msg.getData() instanceof User) {
            Utils.currUser = (User) msg.getData();
            currUserRole = Utils.currUser.getRole();
        }
        LoginScreenFXController.loginStatusUpdated(loginStatus,currUserRole);
    }
}

    /**
     * Set 'is_logged_in' column in DB to 0
     * @param username to logout
     
    public static void logoutUser(String username) {
        Utils.currUser = null;
        HashMap<String, String> args = new HashMap<>();
        args.put("username", username);
        Message msg = new Message(Task.LOGOUT_USERNAME, args);
        ZerliClientController.accept(msg);
    }
}
    */