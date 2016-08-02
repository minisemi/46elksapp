package a46elks.a46elksapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import a46elks.a46elksapp.dataBase.DataBaseContract;
import a46elks.a46elksapp.dataBase.DataBaseHelper;
import a46elks.a46elksapp.tabLayout.Contacts.Contact;

/**
 * Created by Alexander on 2016-07-22.
 */
public class SessionManager {

    private static DataBaseHelper mDbHelper;
    private static SQLiteDatabase writableDatabase;
    private static SQLiteDatabase readableDatabase;
    private static SharedPreferences pref;
    private static int highestContactID = 0;

    // Editor for Shared preferences
    private static SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;


    // Sharedpref file name
    private static String PREF_NAME;
    //private String PREF_NAME;

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

    public void createLoginSession ( String apiPassword, String apiUserName){
        PREF_NAME = apiUserName;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS | PRIVATE_MODE);
        mDbHelper = new DataBaseHelper(context, PREF_NAME+".db");
        writableDatabase = mDbHelper.getWritableDatabase();
        readableDatabase = mDbHelper.getReadableDatabase();

        //pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        // Storing login value as TRUE
        editor.clear();
        editor.putBoolean(IS_LOGIN, true);


        // Storing name in pref

        editor.putString("id", apiUserName);

        // Storing pw in pref
        editor.putString("secret", apiPassword);

        /*if (!pref.contains("groups") || !pref.contains("contacts")) {
            HashSet<String> hashSetGroups = new HashSet<>();
            HashSet<String> hashSetContacts = new HashSet<>();
            editor.putStringSet("groups", hashSetGroups);
            editor.putStringSet("contacts", hashSetContacts);
        }*/
        //editor.remove("contacts");

        // apply changes. MIGHT NEED TO REPLACE APPLY WITH COMMIT.
        editor.commit();




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
        //pref.getStringSet("groups", null).add(group);
        HashSet<String> hashSetGroups = (HashSet<String>) pref.getStringSet("groups", null);
        HashSet<String> groupHashSet = new HashSet<String>();
        hashSetGroups.add(group);
       // editor = pref.edit();
        editor.putStringSet(group, groupHashSet);
        editor.putStringSet("groups", hashSetGroups);
        editor.commit();
    }

    public void removeGroup (String group){
        HashSet<String> hashSetGroups = (HashSet<String>) pref.getStringSet("groups", null);
        hashSetGroups.remove(group);
        //pref.getStringSet("groups", null).remove(group);
       // editor = pref.edit();
        editor.putStringSet("groups", hashSetGroups);
        editor.remove(group);
        editor.commit();

    }

    public List<String> getGroups (){
        List <String> groups = new ArrayList<String>(pref.getStringSet("groups", null));
        return groups;
    }

    public void addContactToGroup (String group, String contact){
        //pref.getStringSet(group, null).add(contact);
        HashSet<String> groupHashSet = (HashSet<String>) pref.getStringSet(group, null);
        groupHashSet.add(contact);
        editor = pref.edit();
        editor.putStringSet(group, groupHashSet);
        editor.commit();
    }

    public void removeContactFromGroup (String group, String contact){
        //pref.getStringSet(group, null).remove(contact);
        HashSet<String> groupHashSet = (HashSet<String>) pref.getStringSet(group, null);
        groupHashSet.remove(contact);
      //  editor = pref.edit();
        editor.putStringSet(group, groupHashSet);
        editor.commit();
    }

    public void createContact (Contact contact){
        //pref.getStringSet("contacts", null).add(contact);
        /*HashSet<String> hashSetContacts = (HashSet<String>) pref.getStringSet("contacts", null);
        hashSetContacts.add(contact);
      //  editor = pref.edit();
        editor.putStringSet("contacts", hashSetContacts);
        editor.commit();*/


// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.ContactsEntry.COLUMN_NAME_CONTACT_ID, contact.getContactID());
        values.put(DataBaseContract.ContactsEntry.COLUMN_NAME_FIRST_NAME, contact.getFirstName());
        values.put(DataBaseContract.ContactsEntry.COLUMN_NAME_LAST_NAME, contact.getLastName());
        values.put(DataBaseContract.ContactsEntry.COLUMN_NAME_MOBILE_NUMBER, contact.getMobileNumber());

// Insert the new row, returning the primary key value of the new row
        writableDatabase.insert(
                DataBaseContract.ContactsEntry.TABLE_NAME,
                DataBaseContract.ContactsEntry.COLUMN_NAME_NULLABLE,
                values);

    }

    public void deleteContact (String contactID){

        // Define 'where' part of query.
        String selection = DataBaseContract.ContactsEntry.COLUMN_NAME_CONTACT_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { contactID };
        // Issue SQL statement.
        writableDatabase.delete(DataBaseContract.ContactsEntry.TABLE_NAME, selection, selectionArgs);



        /*HashSet<String> hashSetContacts = (HashSet<String>) pref.getStringSet("contacts", null);
        hashSetContacts.remove(contact);
        //pref.getStringSet("contacts", null).remove(contact);

        JsonArray containingGroups = jsonParser.parse(contact).getAsJsonObject().get("CONTACT_CONTAINING_GROUPS").getAsJsonArray();

       // editor = pref.edit();
        if (containingGroups.size() != 0) {

            for (int i = 0; i < containingGroups.size(); i++) {
                String group = containingGroups.get(i).getAsString();
                HashSet<String> groupHashSet = (HashSet<String>) pref.getStringSet(group, null);
                groupHashSet.remove(contact);
                editor.putStringSet(group, groupHashSet);
                //pref.getStringSet(group,null).remove(contact);
            }

        }

        editor.putStringSet("contacts", hashSetContacts);
        editor.commit();*/
    }

    public void updateContact (Contact contact){


// New value for one column
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.ContactsEntry.COLUMN_NAME_FIRST_NAME, contact.getFirstName());
        values.put(DataBaseContract.ContactsEntry.COLUMN_NAME_LAST_NAME, contact.getLastName());
        values.put(DataBaseContract.ContactsEntry.COLUMN_NAME_MOBILE_NUMBER, contact.getMobileNumber());


// Which row to update, based on the ID
        String selection = DataBaseContract.ContactsEntry.COLUMN_NAME_CONTACT_ID + " LIKE ?";
        String[] selectionArgs = { contact.getContactID() };

        writableDatabase.update(
                DataBaseContract.ContactsEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);


       // editor = pref.edit();
        //pref.getStringSet("contacts", null).remove(oldContact);
        //pref.getStringSet("contacts", null).add((newContact));
        /*HashSet<String> hashSetContacts = (HashSet<String>) pref.getStringSet("contacts", null);
        hashSetContacts.remove(oldContact);
        hashSetContacts.add(newContact);
        editor.putStringSet("contacts", hashSetContacts);

        JsonArray containingGroups = jsonParser.parse(oldContact).getAsJsonObject().get("CONTACT_CONTAINING_GROUPS").getAsJsonArray();

        if (containingGroups.size() != 0) {

            for (int i = 0; i < containingGroups.size(); i++) {
                String group = containingGroups.get(i).getAsString();
                //pref.getStringSet(group,null).remove(oldContact);
                //pref.getStringSet(group,null).add(newContact);
                HashSet<String> groupHashSet = (HashSet<String>) pref.getStringSet(group, null);
                groupHashSet.remove(oldContact);
                groupHashSet.add(newContact);
                editor.putStringSet(group, groupHashSet);

            }

        }
        if (!containingGroups.isEmpty()) {

            for (String group : containingGroups){
                pref.getStringSet(group, null).remove(oldContact);
                pref.getStringSet(group, null).add(newContact);
                /*HashSet<String> groupHashSet = (HashSet<String>) pref.getStringSet(group, null);
                groupHashSet.add(contact);
                editor.putStringSet(group, groupHashSet);
            }

        }
        editor.commit();*/
    }

    public List<Contact> getContacts (){
        List<Contact> contactsList = new ArrayList<Contact>();

        //hp = new HashMap();
        Cursor cursor =  readableDatabase.rawQuery( "select * from " + DataBaseContract.ContactsEntry.TABLE_NAME, null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String contactID = cursor.getString(cursor.getColumnIndex(DataBaseContract.ContactsEntry.COLUMN_NAME_CONTACT_ID));

            ContentValues values = new ContentValues();
            values.put(DataBaseContract.ContactsEntry.COLUMN_NAME_CONTACT_ID, highestContactID);
            String selection = DataBaseContract.ContactsEntry.COLUMN_NAME_CONTACT_ID + " LIKE ?";
            String[] selectionArgs = { contactID };

            writableDatabase.update(
                    DataBaseContract.ContactsEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);

            String firstName = cursor.getString(cursor.getColumnIndex(DataBaseContract.ContactsEntry.COLUMN_NAME_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(DataBaseContract.ContactsEntry.COLUMN_NAME_LAST_NAME));
            String mobileNumber = cursor.getString(cursor.getColumnIndex(DataBaseContract.ContactsEntry.COLUMN_NAME_MOBILE_NUMBER));


            Contact contact = new Contact(firstName, lastName, mobileNumber);
            contactsList.add(contact);
            cursor.moveToNext();
            highestContactID++;
        }

        return contactsList;

       // return new ArrayList<String>(pref.getStringSet("contacts", null));
    }

    public int getHighestContactID (){ return highestContactID;}

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

    public String getPrefName (){
        return PREF_NAME;
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
