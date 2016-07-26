package a46elks.a46elksapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Alexander on 2016-07-22.
 */
public class SessionManager {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    //private static String PREF_NAME;

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    private static final String KEY_API_USERNAME = "id";

    // Email address (make variable public to access from outside)
    private static final String KEY_API_PASSWORD = "secret";

    // Groups
    private HashSet groups;


    // Constructor
    public SessionManager(Context context){
        this.context = context;
        groups = new HashSet();
    }

    public void createLoginSession (String apiUsername, String apiPassword){
        final String PREF_NAME = apiUsername;

        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString("id", apiUsername);

        // Storing email in pref
        editor.putString("secret", apiPassword);

        editor.putStringSet("groups", groups);
        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put("id", pref.getString("id", null));

        // user email id
        user.put("secret", pref.getString("secret", null));

        // return user
        return user;
    }

    public void createGroup (){
        groups.add("Hej");

    }

    public void updateGroup (){

    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting Login Activity
        context.startActivity(i);
    }
}
