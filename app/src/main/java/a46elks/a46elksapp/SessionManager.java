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
import a46elks.a46elksapp.tabLayout.Groups.Group;

/**
 * Created by Alexander on 2016-07-22.
 */
public class SessionManager {

    private static DataBaseHelper mDbHelper;
    private static SQLiteDatabase writableDatabase;
    private static SQLiteDatabase readableDatabase;
    private static SharedPreferences pref;
    private static int highestContactID = 0, highestGroupID = 0;
    private static ArrayList<Contact> contactsList;
    private static ArrayList<Group> groupsList;


    // Editor for Shared preferences
    private static SharedPreferences.Editor editor;

    // Context
    private static Context context;

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


    // Constructor
    public SessionManager(){
    }

    public void createLoginSession (Context context, String apiPassword, String apiUserName){
        this.context = context;
        PREF_NAME = apiUserName;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS | PRIVATE_MODE);
        mDbHelper = new DataBaseHelper(context, PREF_NAME+".db");
        writableDatabase = mDbHelper.getWritableDatabase();
        readableDatabase = mDbHelper.getReadableDatabase();
        contactsList = new ArrayList<Contact>();

        //pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        // Storing login value as TRUE
        editor.clear();
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref

        editor.putString("id", apiUserName);

        // Storing pw in pref
        editor.putString("secret", apiPassword);
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

    public void createGroup (Group group){

        ContentValues values = new ContentValues();
        values.put(DataBaseContract.GroupsEntry.COLUMN_NAME_GROUP_ID, group.getGroupID());
        values.put(DataBaseContract.GroupsEntry.COLUMN_NAME_GROUP_NAME, group.getName());

// Insert the new row, returning the primary key value of the new row
        writableDatabase.insert(
                DataBaseContract.GroupsEntry.TABLE_NAME,
                DataBaseContract.GroupsEntry.COLUMN_NAME_NULLABLE,
                values);

        ArrayList<Contact> containingContacts = group.getContainingContacts();

        if (containingContacts.size()>0){

            for (Contact contact : containingContacts){

            values = new ContentValues();
            values.put(DataBaseContract.ContainingEntry.COLUMN_NAME_GROUP_ID, group.getGroupID());
            values.put(DataBaseContract.ContainingEntry.COLUMN_NAME_CONTACT_ID, contact.getContactID());

// Insert the new row, returning the primary key value of the new row
            writableDatabase.insert(
                    DataBaseContract.ContainingEntry.TABLE_NAME,
                    DataBaseContract.ContainingEntry.COLUMN_NAME_NULLABLE,
                    values);
            }

        }
    }

    public void deleteGroup (Group group){

        // Define 'where' part of query.
        String selection = DataBaseContract.GroupsEntry.COLUMN_NAME_GROUP_ID + " LIKE ?";

        String groupID = group.getGroupID();
        // Specify arguments in placeholder order.
        String[] selectionArgs = { groupID };
        // Issue SQL statement.
        writableDatabase.delete(DataBaseContract.GroupsEntry.TABLE_NAME, selection, selectionArgs);

        ArrayList<Contact> containingContacts = group.getContainingContacts();

        if (containingContacts.size()>0){

// Insert the new row, returning the primary key value of the new row
                writableDatabase.delete(
                        DataBaseContract.ContainingEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

        }

    }

    public void updateGroup (Group group){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.GroupsEntry.COLUMN_NAME_GROUP_NAME, group.getName());

// Which row to update, based on the ID
        String selection = DataBaseContract.GroupsEntry.COLUMN_NAME_GROUP_ID + " LIKE ?";
        String[] selectionArgs = { group.getGroupID() };

        writableDatabase.update(
                DataBaseContract.GroupsEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

    }

    // Called upon start
    public void initiateGroups(){
        groupsList = new ArrayList<Group>();

        //hp = new HashMap();
        Cursor cursor =  readableDatabase.rawQuery( "select * from " + DataBaseContract.GroupsEntry.TABLE_NAME, null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String groupID = cursor.getString(cursor.getColumnIndex(DataBaseContract.GroupsEntry.COLUMN_NAME_GROUP_ID));

            ContentValues values = new ContentValues();
            values.put(DataBaseContract.GroupsEntry.COLUMN_NAME_GROUP_ID, highestGroupID);
            String selection = DataBaseContract.GroupsEntry.COLUMN_NAME_GROUP_ID + " LIKE ?";
            String[] selectionArgs = { groupID };

            writableDatabase.update(
                    DataBaseContract.GroupsEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);

            writableDatabase.update(
                    DataBaseContract.ContainingEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);


            String groupName = cursor.getString(cursor.getColumnIndex(DataBaseContract.GroupsEntry.COLUMN_NAME_GROUP_NAME));

            Cursor containingCursor =  readableDatabase.rawQuery( "select " + DataBaseContract.ContainingEntry.COLUMN_NAME_CONTACT_ID +
                    " from " + DataBaseContract.ContainingEntry.TABLE_NAME +
                    " where " + DataBaseContract.ContainingEntry.COLUMN_NAME_GROUP_ID + " = " + groupID, null );

            containingCursor.moveToFirst();
            ArrayList<Contact> containingContacts = new ArrayList<>();

            while(!containingCursor.isAfterLast()){

                int contactID = containingCursor.getInt(containingCursor.getColumnIndex(DataBaseContract.ContainingEntry.COLUMN_NAME_CONTACT_ID));
                containingContacts.add(contactsList.get(contactID));
                containingCursor.moveToNext();
            }

            Group group = new Group(groupName, containingContacts);
            groupsList.add(group);
            cursor.moveToNext();
            highestGroupID++;
        }

    }

    public ArrayList<Group> getGroups (){
        return groupsList;
    }

    public void addContactToGroup (Group group, Contact contact){
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.ContainingEntry.COLUMN_NAME_GROUP_ID, group.getGroupID());
        values.put(DataBaseContract.ContainingEntry.COLUMN_NAME_CONTACT_ID, contact.getContactID());

// Insert the new row, returning the primary key value of the new row
        writableDatabase.insert(
                DataBaseContract.ContainingEntry.TABLE_NAME,
                DataBaseContract.ContainingEntry.COLUMN_NAME_NULLABLE,
                values);
    }

    public void removeContactFromGroup (Group group, Contact contact){

        // Define 'where' part of query.
        String selection = DataBaseContract.ContainingEntry.COLUMN_NAME_GROUP_ID +
                ", " + DataBaseContract.ContainingEntry.COLUMN_NAME_CONTACT_ID +
                " WHERE " + DataBaseContract.ContainingEntry.COLUMN_NAME_GROUP_ID + " = ?" +
                " AND " + DataBaseContract.ContainingEntry.COLUMN_NAME_CONTACT_ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { group.getGroupID(), contact.getContactID() };
        // Issue SQL statement.
        writableDatabase.delete(DataBaseContract.ContainingEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void createContact (Contact contact){


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

        contactsList.add(contact);

    }

    public void deleteContact (String contactID){

        // Define 'where' part of query.
        String selection = DataBaseContract.ContactsEntry.COLUMN_NAME_CONTACT_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { contactID };
        // Issue SQL statement.
        writableDatabase.delete(DataBaseContract.ContactsEntry.TABLE_NAME, selection, selectionArgs);

        writableDatabase.delete(
                DataBaseContract.ContainingEntry.TABLE_NAME,
                selection,
                selectionArgs);

        contactsList.remove(contactID);
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

        int contactID = Integer.parseInt(contact.getContactID());
        /*contactsList.get(contactID).setFirstName(contact.getFirstName());
        contactsList.get(contactID).setLastName(contact.getLastName());
        contactsList.get(contactID).setMobileNumber(contact.getMobileNumber());*/

    }

    // Called upon start
    public void initiateContacts(){

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

            writableDatabase.update(
                    DataBaseContract.ContainingEntry.TABLE_NAME,
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


    }

    public ArrayList<Contact> getContacts (){
        return contactsList;
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
