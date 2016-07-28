package a46elks.a46elksapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // Groups and contacts
    //private HashSet<String> groups, contacts;
    //private HashMap<String, HashSet<String>> groupMap;


    // Constructor
    public SessionManager(Context context){
        this.context = context;
        /*groups = new HashSet<String>();
        contacts = new HashSet<String>();
        groupMap = new HashMap<>();*/

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

        if (!pref.contains("groups") || !pref.contains("contacts")) {
            HashSet<String> hashSetGroups = new HashSet<>();
            HashSet<String> hashSetContacts = new HashSet<>();
            editor.putStringSet("groups", hashSetGroups);
            editor.putStringSet("contacts", hashSetContacts);
        }

        // apply changes. MIGHT NEED TO REPLACE APPLY WITH COMMIT.
        editor.apply();


       /* for (String group : pref.getStringSet("groups", null)){
            groupMap.put(group, (HashSet<String>) pref.getStringSet(group, null));
            groups.add(group);
        }

        for (String contact : pref.getStringSet("contacs", null)) {
            contacts.add(contact);
        }*/
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

    public void createGroup (String group){
        pref.getStringSet("groups", null).add(group);
        //HashSet<String> hashSetGroups = (HashSet<String>) pref.getStringSet("groups", null);
        HashSet<String> groupHashSet = new HashSet<String>();
       // hashSetGroups.add(group);
        editor = pref.edit();
        editor.putStringSet(group, groupHashSet);
        //editor.putStringSet("groups", hashSetGroups);
        editor.apply();
    }

    public void removeGroup (String group){
        //HashSet<String> hashSetGroups = (HashSet<String>) pref.getStringSet("groups", null);
        //hashSetGroups.remove(group);
        pref.getStringSet("groups", null).remove(group);
        editor = pref.edit();
       // editor.putStringSet("groups", hashSetGroups);
        editor.remove(group);
        editor.apply();

    }

    public void addContactToGroup (String group, String contact){
        pref.getStringSet(group, null).add(contact);
        /*HashSet<String> groupHashSet = (HashSet<String>) pref.getStringSet(group, null);
        groupHashSet.add(contact);
        editor = pref.edit();
        editor.putStringSet(group, groupHashSet);
        editor.apply();*/
    }

    public void removeContactFromGroup (String group, String contact){
        pref.getStringSet(group, null).remove(contact);
        /*HashSet<String> groupHashSet = (HashSet<String>) pref.getStringSet(group, null);
        groupHashSet.remove(contact);
        editor = pref.edit();
        editor.putStringSet(group, groupHashSet);
        editor.apply();*/
    }

    public void createContact (String contact){
        pref.getStringSet("contacts", null).add(contact);
        /*HashSet<String> hashSetContacts = (HashSet<String>) pref.getStringSet("contacts", null);
        hashSetContacts.add(contact);
        editor = pref.edit();
        editor.putStringSet("contacts", hashSetContacts);
        editor.apply();*/
    }

    // TODO: FIX ARRAYLIST/HASHSET ON CONTAINING GROUPS
    public void deleteContact (String contact){
        pref.getStringSet("contacts", null).remove(contact);

        if (!containingGroups.isEmpty()) {

            for (String group : containingGroups){
                pref.getStringSet(group, null).remove(contact);
                /*HashSet<String> groupHashSet = (HashSet<String>) pref.getStringSet(group, null);
                groupHashSet.add(contact);
                editor.putStringSet(group, groupHashSet);*/
            }

        }
        HashSet<String> hashSetContacts = (HashSet<String>) pref.getStringSet("contacts", null);
        hashSetContacts.remove(contact);
        editor = pref.edit();
        editor.putStringSet("contacts", hashSetContacts);
        editor.apply();
    }

    public void updateContact (String oldContact, String newContact){
        pref.getStringSet("contacts", null).remove(oldContact);
        pref.getStringSet("contacts", null).add((newContact));
        if (!containingGroups.isEmpty()) {

            for (String group : containingGroups){
                pref.getStringSet(group, null).remove(oldContact);
                pref.getStringSet(group, null).add(newContact);
                /*HashSet<String> groupHashSet = (HashSet<String>) pref.getStringSet(group, null);
                groupHashSet.add(contact);
                editor.putStringSet(group, groupHashSet);*/
            }

        }
        /*HashSet<String> hashSetContacts = (HashSet<String>) pref.getStringSet("contacts", null);
        editor = pref.edit();
        hashSetContacts.add(contact);
        editor.putStringSet("contacts", hashSetContacts);
        editor.apply();*/
    }



    public String getNumber (String contact){ return pref.getString(contact, null);}

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
