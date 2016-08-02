package a46elks.a46elksapp.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexander on 2016-08-01.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    //public static final String DATABASE_NAME = "FeedReader.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TABLE_CONTACTS =
            "CREATE TABLE " + DataBaseContract.ContactsEntry.TABLE_NAME + " (" +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_CONTACT_ID + " INTEGER PRIMARY KEY," +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_MOBILE_NUMBER + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_CONTAINING_GROUPS + TEXT_TYPE +
    //... // Any other options for the CREATE command
            " )";
    private static final String SQL_CREATE_TABLE_GROUPS =
            "CREATE TABLE " + DataBaseContract.GroupEntry.TABLE_NAME + " (" +
                    DataBaseContract.ContactsEntry._ID + " INTEGER PRIMARY KEY," +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_MOBILE_NUMBER + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_CONTAINING_GROUPS + TEXT_TYPE +
    //... // Any other options for the CREATE command
            " )";
    /*private static final String SQL_CREATE_TABLE_HISTORY =
            "CREATE TABLE " + DataBaseContract.ContactsEntry.TABLE_NAME + " (" +
                    DataBaseContract.ContactsEntry._ID + " INTEGER PRIMARY KEY," +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_MOBILE_NUMBER + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_CONTAINING_GROUPS + TEXT_TYPE + COMMA_SEP +
    //... // Any other options for the CREATE command
            " )";
    private static final String SQL_CREATE_TABLE_MESSAGES =
            "CREATE TABLE " + DataBaseContract.ContactsEntry.TABLE_NAME + " (" +
                    DataBaseContract.ContactsEntry._ID + " INTEGER PRIMARY KEY," +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_MOBILE_NUMBER + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.ContactsEntry.COLUMN_NAME_CONTAINING_GROUPS + TEXT_TYPE + COMMA_SEP +
    //... // Any other options for the CREATE command
            " )";*/

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DataBaseContract.ContactsEntry.TABLE_NAME;

    public DataBaseHelper(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CONTACTS);
        //db.execSQL(SQL_CREATE_TABLE_GROUPS);
        //db.execSQL(SQL_CREATE_TABLE_HISTORY);
        //db.execSQL(SQL_CREATE_TABLE_MESSAGES);
    }

    public void createGroup(SQLiteDatabase db, String group){
        String SQL_CREATE_TABLE_GROUP =
                "CREATE TABLE " + group.toLowerCase() + " (" +
                        DataBaseContract.ContactsEntry.COLUMN_NAME_CONTACT_ID + " INTEGER FOREIGN KEY," +
                        //... // Any other options for the CREATE command
                        " )";
        db.execSQL(SQL_CREATE_TABLE_GROUP);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
