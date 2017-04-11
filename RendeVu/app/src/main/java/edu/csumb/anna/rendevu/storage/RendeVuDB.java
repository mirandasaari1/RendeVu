package edu.csumb.anna.rendevu.storage;


import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sal on 4/11/2017.
 */

public class RendeVuDB {
    final String TAG = "UsersDB";

    //when making a change to the database update the db version
    // database constants
    public static final String DB_NAME = "RendeVuDB.db";
    public static final int    DB_VERSION = 5;

    //users table constants
    public static final String USERS_TABLE = "users";

    public static final String USERS_ID = "_id";
    public static final int    USERS_ID_COL = 0;

    public static final String USERS_FIRST_NAME = "first_name";
    public static final int    USERS_FIRST_NAME_COL = 1;

    public static final String USERS_LAST_NAME = "last_name";
    public static final int    USERS_LAST_NAME_COL = 2;

    public static final String USERS_IMG_URL = "image_url";
    public static final int    USERS_IMG_URL_COL = 3;

    public static final String USERS_EMAIL = "email";
    public static final int    USERS_EMAIL_COL = 4;

    // CREATE and DROP TABLE statements
    public static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + USERS_TABLE + " (" +
                    USERS_ID   + " TEXT PRIMARY KEY, " +
                    USERS_FIRST_NAME + " TEXT, "+
                    USERS_LAST_NAME + " TEXT, "+
                    USERS_IMG_URL + " TEXT, "+
                    USERS_EMAIL + " TEXT);";

//    public static final String CREATE_LIST_TABLE =
//            "CREATE TABLE " + LIST_TABLE + " (" +
//                    LIST_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    LIST_NAME + " TEXT    NOT NULL UNIQUE);";
//
//    public static final String CREATE_TASK_TABLE =
//            "CREATE TABLE " + TASK_TABLE + " (" +
//                    TASK_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    TASK_LIST_ID    + " INTEGER NOT NULL, " +
//                    TASK_NAME       + " TEXT    NOT NULL, " +
//                    TASK_NOTES      + " TEXT, " +
//                    TASK_COMPLETED  + " TEXT, " +
//                    TASK_HIDDEN     + " TEXT, " +
//                    TASK_FEE        + " DOUBLE);";

    public static final String DROP_USERS_TABLE =
            "DROP TABLE IF EXISTS " + USERS_TABLE;

//    public static final String DROP_LIST_TABLE =
//            "DROP TABLE IF EXISTS " + LIST_TABLE;
//
//    public static final String DROP_TASK_TABLE =
//            "DROP TABLE IF EXISTS " + TASK_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CREATE_USERS_TABLE);
//            db.execSQL(CREATE_LIST_TABLE);
//            db.execSQL(CREATE_TASK_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("RendeVu DB", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(RendeVuDB.DROP_USERS_TABLE);
//            db.execSQL(RendeVuDB.DROP_LIST_TABLE);
//            db.execSQL(RendeVuDB.DROP_TASK_TABLE);
            onCreate(db);
        }
    }

    // database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public RendeVuDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }
    /**
     * Inserts a user to the database
     * @param
     * @return rowID as an int
     */
    public void insertUser( String uID, String fullName, String anEmail, String imgUrl) {

        if (userExists(uID)) {
            Log.d("RendeVuDB", "user already in DB");
            return;
        }

        String firstName = "";
        String lastName = "";

        try{
            String[] split = fullName.split(" ");
            firstName = split[0];
            lastName = split[1];
            Log.d("RendeVuDB", firstName);
            Log.d("RendeVuDB", lastName);
        }catch (ArrayIndexOutOfBoundsException ex){

        }

        ContentValues cv = new ContentValues();
        cv.put(USERS_ID, uID);
        cv.put(USERS_EMAIL, anEmail);
        cv.put(USERS_FIRST_NAME, firstName);
        cv.put(USERS_LAST_NAME, lastName);
        cv.put(USERS_IMG_URL, imgUrl);

        this.openWriteableDB();
        long rowID = db.insert(USERS_TABLE, null, cv);
        this.closeDB();

        //return rowID;
    }

    /**
     * Checks the database to see if the user exists
     * @param anID
     * @return true if the user exists
     */
    public boolean userExists(String anID) {
        this.openReadableDB();
        Cursor cur = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE "+USERS_ID+" = '" + anID + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        this.closeDB();
        return exist;

    }

    //END USER METHODS
    //////////////////////////////////////////////////////////////////////////////////
}
