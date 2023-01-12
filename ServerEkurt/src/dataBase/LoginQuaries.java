package dataBase;

import logic.User;
import common.*;
import enums.RegionEnum;
import enums.RoleEnum;
import enums.StatusEnum;
import Utils.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class defines the methods that send query's to the DB and receive the
 * data back from the DB and return it to the relevant controllers and classes
 * that need to use that information
 */
public class LoginQuaries {
    public static void loginByUsernameAndPassword(Transaction msg) {
        HashMap<String, String> args = (HashMap<String, String>) msg.getData();
        String username = args.get("username");
        String password = args.get("password");
        User currentUser = null;
        ResultSet rs = dbController.getInstance().executeQuery("SELECT * FROM ekurt.users where username='" + username + "' and password='" + password + "'");
		if (rs == null)
			msg.setResponse(Response.FAILED);
		else {
			try {
				if (rs.next()) {
					currentUser = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
							rs.getBoolean("is_logged_in"), rs.getString("firstname"), rs.getString("lastname"),
							rs.getString("email"), rs.getString("telephone"), RoleEnum.valueOf(rs.getString("role")),
							StatusEnum.valueOf(rs.getString("status")), RegionEnum.valueOf(rs.getString("region")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (currentUser == null) {
				msg.setResponse(Response.INCORRECT_VALUES);
			}else if(!currentUser.isLoggedIn()) {
				updateIsLoggedInColumn(username, password, 1);
				currentUser.setLoggedIn(true);
				msg.setData(currentUser);
				msg.setResponse(Response.LOGGED_IN_SUCCESS);
			} else if (currentUser.isLoggedIn()) {
				msg.setResponse(Response.ALREADY_LOGGED_IN);
			} else {
				msg.setResponse(Response.INCORRECT_VALUES);
			}
		}
	}

	/**
	 * Set 'is_logged_in' column in DB to 0
	 * 
	 * @param msg contains HashMap with 'username'
	 */
	public static void logoutUsername(Transaction msg) {
		@SuppressWarnings("unchecked")
		HashMap<String, String> args = (HashMap<String, String>) msg.getData();
		String username = args.get("username");
		updateIsLoggedInColumn(username, null, 0);
		msg.setResponse(Response.LOGGEDOUT_SUCCESSFULLY);
	}

	/**
	 * Check if a user is already flagged as 'logged in', in the DB
	 * 
	 * @param rs
	 * @return True if logged in, False otherwise
	 */
	@SuppressWarnings("unused")
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
		if (password == null) {
			query = "UPDATE ekurt.users SET is_logged_in = " + value + " WHERE username = '" + username + "'";
        }else {
        	query = "UPDATE ekurt.users SET is_logged_in = " + value + " WHERE username = '" + username + "' AND password = '" + password + "'";
        }
        int affectedRows = dbController.getInstance().executeUpdate(query);
        System.out.println("affected rows" + affectedRows);
        return (affectedRows == 1);
    }
}