package edu.columbia.enp2111.rallypoint;

/**
 * This class contains methods that deal with the network associated with the
 * user using the application. This ensures that only the destinations and
 * departure points associated with this network are shown.
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

public class NetworkFunctions
{     
    private JSONParser jsonParser;
    private static final String TAG_GET_NETWORK_INFO = "get_network_info";
    private static final String KEY_NETWORK = "network";
    
    /** Constructor. */
    public NetworkFunctions()
    {
    	jsonParser = new JSONParser();
    }
     
    /**
     * Method to make a network get request.
     * @param email address (serves as the username) entered by user
     * @param password entered by user
     * */
    public JSONObject getUserNetwork(String network)
    {
        // Building parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(JSONParser.KEY_TAG, TAG_GET_NETWORK_INFO));
        params.add(new BasicNameValuePair(KEY_NETWORK, network));
        JSONObject json = jsonParser.getJSONFromUrl(JSONParser.API_URL, params);
        return json;
    }

    /** Returns this network's name. */
    public String getName(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(context);
    	HashMap<String, String> network = db.getNetworkDetails();
    	String name = network.get(DatabaseHandler.KEY_NETWORK_NAME);
    	return name;
    }
    
    /** Returns this network's non-campus destinations in the form of an 
     * array. */
    public String[] getNonCampusPlaces(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(context);
    	HashMap<String, String> network = db.getNetworkDetails();
    	String destinations = network.get(DatabaseHandler.KEY_NON_CAMPUS_PLACES);
    	String[] destinationArray = destinations.split("#");
    	// remove all trailing and leading spaces on the destination names
    	for (int i = 0; i < destinationArray.length; i++)
    		destinationArray[i] = destinationArray[i].trim();
    	return destinationArray;
    }
    
    /** Returns this network's non-campus destinations in the form of an 
     * array. */
    public String[] getCampusPlaces(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(context);
    	HashMap<String, String> network = db.getNetworkDetails();
    	String destinations = network.get(DatabaseHandler.KEY_CAMPUS_PLACES);
    	String[] destinationArray = destinations.split("#");
    	// remove all trailing and leading spaces on the destination names
    	for (int i = 0; i < destinationArray.length; i++)
    		destinationArray[i] = destinationArray[i].trim();
    	return destinationArray;
    }
    
    /** Method to clear network data and reset database. */
    public boolean clearNetwork(Context context)
    {
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetNetworkTable();
        return true;
    }   
}