package edu.columbia.enp2111.rallypoint;

/**
 * This class contains methods that deal with the user using the application.
 * No other users' details can be accessed. 
 * 
 * @author Emily Pakulski, modified Tamada's code
 * @author Ravi Tamada, original code from tutorial on AndroidHive.info
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class UserFunctions
{     
	/* tags for what index.php should do: 
	 * NOTE: If making changes to these constants, changes must be made on
	 * the API_URL's index.php file, too.
	 */
    public static final String LOGIN_USER_TAG = "login";
    public static final String REGISTER_USER_TAG = "register";
	
	private JSONParser jsonParser;

    /** Constructor. */
    public UserFunctions()
    {
    	jsonParser = new JSONParser();
    }
     
    /**
     * Method to make a login request.
     * @param email address (serves as the username) entered by user
     * @param password entered by user
     * */
    public JSONObject loginUser(String email, String password)
    {
        // Building parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(JSONParser.KEY_TAG, LOGIN_USER_TAG));
        params.add(new BasicNameValuePair(DatabaseHandler.KEY_EMAIL, email));
        params.add(new BasicNameValuePair(DatabaseHandler.KEY_PASSWORD, password));
        JSONObject json = jsonParser.getJSONFromUrl(JSONParser.API_URL, params);
        return json;
    }
     
    /**
     * Method to make a registration request.
     * @param name: user provided name
     * @param email: user's email address
     * @param network: campus network derived from email address
     * @param password: user provided pass
     * */
    public JSONObject registerUser(String name, String email, String network, String password)
    {
        // Building parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(JSONParser.KEY_TAG, REGISTER_USER_TAG));
        params.add(new BasicNameValuePair(DatabaseHandler.KEY_NAME, name));
        params.add(new BasicNameValuePair(DatabaseHandler.KEY_EMAIL, email));
        params.add(new BasicNameValuePair(DatabaseHandler.KEY_NETWORK, network));
        params.add(new BasicNameValuePair(DatabaseHandler.KEY_PASSWORD, password));
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(JSONParser.API_URL, params);
        return json; // return json
    }
     
    /**
     * Method to get login status. (See DatabaseHandler class for more info
     * on how this works.)
     * @return true if logged in, false if not.
     * */
    public boolean isUserLoggedIn(Context context)
    {
    	Log.v("Testing", "Is the user logged in?");
    	DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getLoginRowCount();
        if(count > 0) // user logged in
        {
        	return true;
        }
        Log.v("Testing", "no the user isn't logged in");
        return false;
    }

    /** Returns this user's UID. */
    public String getID(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(context);
    	HashMap<String, String> user = db.getUserDetails();
    	return user.get(DatabaseHandler.KEY_UID);
    }
    
    /** Returns this user's name. */
    public String getName(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(context);
    	HashMap<String, String> user = db.getUserDetails();
    	return user.get(DatabaseHandler.KEY_NAME);
    }
    
    /** Returns this user's email. */
    public String getEmail(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(context);
    	HashMap<String, String> user = db.getUserDetails();
    	return user.get(DatabaseHandler.KEY_EMAIL);
    }
    
    /** Returns this user's network. */
    public String getNetworkName(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(context);
    	HashMap<String, String> user = db.getUserDetails();
    	String networkVal = user.get("network").toLowerCase();
    	if (networkVal.equals("columbia"))
    		return "Columbia University";
    	if (networkVal.equals("barnard"))
    		return "Barnard College";
    	return "Default";
    }
     
    /** Method to logout user and reset database. */
    public boolean logoutUser(Context context)
    {
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
     
}