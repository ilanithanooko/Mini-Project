package dataBase;


import logic.User;
import common.*;
import Utils.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
/**
 * This class defines the methods that send query's to the DB and receive the data back from the DB
 * and return it to the relevant controllers and classes that need to use that information
 */
public class LoginQuaries {
    public static void loginByUsernameAndPassword(Transaction msg) {
        HashMap<String, String> args = (HashMap<String, String>) msg.getData();
        String username = args.get("username");
        String password = args.get("password");

        ResultSet rs = dbController.getInstance().executeQuery("SELECT * FROM ekurt.users where username='" + username + "' and password='" + password + "'");
        if (rs == null)
            msg.setResponse(Response.FAILED);
        else {
            if (updateIsLoggedInColumn(username, password, 1)) {
                User currentUser = generalMethods.getFirstElementFromList(User.createUserListFromResultSet(rs));
                //currentUser.setAccount(getAccountInfoByUserID(currUser.getId()));
                //TODO Remove these comments to check if user is already logged in (and acts accordingly)
                if (currentUser.isLoggedIn()) {
                    msg.setResponse(Response.ALREADY_LOGGED_IN);
                } else {
                    msg.setData(currentUser);
                    msg.setResponse(Response.SUCCEED);
                }
            } else {
                msg.setResponse(Response.INCORRECT_VALUES);
            }
        }
    }

    /**
     * Set 'is_logged_in' column in DB to 0
     * @param msg contains HashMap with 'username'
     */
    public static void logoutUsername(Transaction msg) {
        HashMap<String, String> args = (HashMap<String, String>) msg.getData();
        String username = args.get("username");
        updateIsLoggedInColumn(username, null, 0);
    }

    /**
     * Check if a user is already flagged as 'logged in', in the DB
     * @param rs
     * @return True if logged in, False otherwise
     */
    private static boolean isAlreadyLoggedIn(ResultSet rs) {
        try {
            if (rs.first()) {
                return rs.getBoolean("is_logged_in");
            }
            rs.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update 'is_logged_in' column in DB to 'value' parameter
     *
     * @param username to update his 'is_logged_in' column
     * @param value    0 / 1
     * @param password
     * @return
     */
    private static boolean updateIsLoggedInColumn(String username, String password, int value) {
        if (value != 0 && value != 1)
            throw new IllegalArgumentException("'is_logged_in' column can be 0 or 1!");
        String query;
        if (password == null)
            query = "UPDATE ekurt.users SET is_logged_in = " + value + " WHERE username = '" + username + "'";
        else query = "UPDATE ekurt.users SET is_logged_in = " + value + " WHERE username = '" + username + "' AND password = '" + password + "'";
        int affectedRows = dbController.getInstance().executeUpdate(query);
        return affectedRows == 1;
    }

    /**
     * Get account and credit card info (if exist)
     * @param userID to retrieve his account info
     * @return Account if exist, otherwise null
     */
    /*
    private static Account getAccountInfoByUserID(int userID) {
        String queryFetchAccountInfoByUserID = "SELECT id, balance, status, card_number, cvv, expiration_date \n" +
                "FROM zerli.account\n" +
                "JOIN creditcards ON creditcards.number=account.card_number\n" +
                "AND account.user_id=" + userID;
        ResultSet rs = DatabaseController.getInstance().executeQuery(queryFetchAccountInfoByUserID);
        if (rs != null) {
            return Functions.getFirstElementFromList(Account.createAccountListFromResultSet(rs));
        }
        return null;
    }
}*/
}