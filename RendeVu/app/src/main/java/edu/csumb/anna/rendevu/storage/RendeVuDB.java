package edu.csumb.anna.rendevu.storage;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.csumb.anna.rendevu.data.Chaperone;
import edu.csumb.anna.rendevu.data.Chaperones;
import edu.csumb.anna.rendevu.data.PlannedDate;
import edu.csumb.anna.rendevu.data.PlannedDates;

/**
 * Created by Sal on 4/11/2017.
 */

public class RendeVuDB{
    final String TAG = "RendeVuDB";

    //when making a change to the database update the db version
    // database constants
    public static final String DB_NAME = "RendeVuDB.db";
    public static final int DB_VERSION = 17;

    //users table constants
    public static final String USERS_TABLE = "users";

    public static final String USERS_ID = "_id";
    public static final int USERS_ID_COL = 0;

    public static final String USERS_FIRST_NAME = "first_name";
    public static final int USERS_FIRST_NAME_COL = 1;

    public static final String USERS_LAST_NAME = "last_name";
    public static final int USERS_LAST_NAME_COL = 2;

    public static final String USERS_IMG_URL = "image_url";
    public static final int USERS_IMG_URL_COL = 3;

    public static final String USERS_EMAIL = "email";
    public static final int USERS_EMAIL_COL = 4;

    public static final String USERS_PHONE_NUMBER = "phone_number";
    public static final int USERS_PHONE_NUMBER_COL = 5;

    //add date constants


    public static final String DATES_TABLE = "_dates";

    public static final String DATE_ID = "_date_id";
    public static final int DATE_ID_COL = 0;

    public static final String DATE_NAME = "_date_name";
    public static final int DATE_NAME_COL = 1;

    public static final String DATE_TIME = "_time";
    public static final int DATE_TIME_COL = 2;

    public static final String DATE = "_date";
    public static final int DATE_COL = 3;

    public static final String COMFORT_LEVEL = "_comfort_level";
    public static final int DATE_COMFORT_LEVEL_COL = 4;

    public static final String ADDITIONAL_INFO = "_additional_info";
    public static final int DATE_ADDITIONAL_INFO_COL = 5;

    public static final String START_TIME = "_start_time";
    public static final int DATE_START_TIME_COL = 6;

    public static final String END_TIME = "_end_time";
    public static final int DATE_END_TIME_COL = 7;
    //public static final Calendar calendar = Calendar.getInstance();

    //public static  String current_date="";

    //link to chaperones picked for the date

    //chaperones constants

    public static final String CHAPERONES_TABLE = "chaperones";

    public static final String CHAPERONE_ID = "chaperone_id";
    public static final int CHAPERONE_ID_COL = 0;

    public static final String CHAPERONE_NAME = "chaperone_name";
    public static final int CHAPERONE_NAME_COL = 1;

    public static final String CHAPERONE_PHONE_NUMBER = "chaperone_phone_number";
    public static final int CHAPERONE_PHONE_NUMBER_COL = 2;
    //date_chaperone table constantcs

    public static final String DATE_CHAPERONES_TABLE ="date_chaperones";

    public static final String DATE_CHAPERONE_ID="date_chaperone_id";


    // CREATE and DROP TABLE statements
    public static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + USERS_TABLE + " (" +
                    USERS_ID + " TEXT PRIMARY KEY, " +
                    USERS_FIRST_NAME + " TEXT, " +
                    USERS_LAST_NAME + " TEXT, " +
                    USERS_IMG_URL + " TEXT, " +
                    USERS_PHONE_NUMBER + " TEXT, " +
                    USERS_EMAIL + " TEXT);";

    public static final String CREATE_DATES_TABLE =
            "CREATE TABLE " + DATES_TABLE + " (" +
                    DATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DATE_NAME + " TEXT, " +
                    DATE_TIME + " TEXT, " +
                    DATE + " TEXT, " +
                    COMFORT_LEVEL + " TEXT, " +
                    ADDITIONAL_INFO + " TEXT, " +
                    START_TIME + " TEXT, " +
                    END_TIME + " TEXT);";


    public static final String CREATE_CHAPERONES_TABLE =
            "CREATE TABLE " + CHAPERONES_TABLE + " (" +
                    CHAPERONE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CHAPERONE_NAME + " TEXT, " +
                    CHAPERONE_PHONE_NUMBER + " TEXT);";

    public static final String CREATE_DATE_CHAPERONES_TABLE =
                "CREATE TABLE " + DATE_CHAPERONES_TABLE + " ( " +
                        DATE_CHAPERONE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CHAPERONE_ID + " INTEGER, " +
                        DATE_NAME + " INTEGER);";

    //variables
    private String chaperoneID="";


    public static final String DROP_USERS_TABLE =
            "DROP TABLE IF EXISTS " + USERS_TABLE;

    public static final String DROP_DATES_TABLE =
            "DROP TABLE IF EXISTS " + DATES_TABLE;

    public static final String DROP_CHAPERONES_TABLE =
            "DROP TABLE IF EXISTS " + CHAPERONES_TABLE;

    public static final String DROP_DATE_CHAPERONES_TABLE =
            "DROP TABLE IF EXISTS " + DATE_CHAPERONES_TABLE;




    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CREATE_USERS_TABLE);
            db.execSQL(CREATE_DATES_TABLE);
            db.execSQL(CREATE_CHAPERONES_TABLE);
            db.execSQL(CREATE_DATE_CHAPERONES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("RendeVu DB", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(RendeVuDB.DROP_USERS_TABLE);
            db.execSQL(RendeVuDB.DROP_DATES_TABLE);
            db.execSQL(RendeVuDB.DROP_CHAPERONES_TABLE);
            db.execSQL(RendeVuDB.DROP_DATE_CHAPERONES_TABLE);
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
    //////////////////////////////////////////////////////////////////////////////////
    // USERS METHODS

    /**
     * Inserts a user to the database
     *
     * @param
     * @return rowID as an int
     */
    public void insertUser(String uID, String fullName, String anEmail, String imgUrl) {

        if (userExists(uID)) {
            Log.d("RendeVuDB", "user already in DB");
            return;
        }

        String firstName = "";
        String lastName = "";
        String phoneNumber = null;

        try {
            String[] split = fullName.split(" ");
            firstName = split[0];
            lastName = split[1];
            Log.d("RendeVuDB", firstName);
            Log.d("RendeVuDB", lastName);
        } catch (ArrayIndexOutOfBoundsException ex) {

        }

        ContentValues cv = new ContentValues();
        cv.put(USERS_ID, uID);
        cv.put(USERS_EMAIL, anEmail);
        cv.put(USERS_FIRST_NAME, firstName);
        cv.put(USERS_LAST_NAME, lastName);
        cv.put(USERS_IMG_URL, imgUrl);
        cv.put(USERS_PHONE_NUMBER, phoneNumber);

        this.openWriteableDB();
        long rowID = db.insert(USERS_TABLE, null, cv);
        this.closeDB();

        //return rowID;
    }

    /**
     * Checks the database to see if the user exists
     *
     * @param anID
     * @return true if the user exists
     */
    public boolean userExists(String anID) {
        this.openReadableDB();
        Cursor cur = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USERS_ID + " = '" + anID + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        this.closeDB();
        return exist;

    }

    //insert phone number into users table
    public void insertPhoneNumber(String pNum){
        ContentValues cv = new ContentValues();
        cv.put(USERS_PHONE_NUMBER, pNum);
        this.openWriteableDB();
        long rowId = db.insert(DATES_TABLE, null, cv);
        this.closeDB();
    }


    //END USER METHODS
    //////////////////////////////////////////////////////////////////////////////////
    // DATES METHODS

    // Insert a date into the database
    public void testDate(String cName) {

        ContentValues cv = new ContentValues();
        cv.put(DATE_NAME, cName);

        this.openWriteableDB();
        long rowID = db.insert(DATES_TABLE, null, cv);
        this.closeDB();
    }



    public int deleteDateFromDB(int id){
        String where = DATE_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(DATES_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    // Insert a date into the database
    public void insertDate(String cName, String cDate, String dTime, String cLevel, String aInfo) {

        ContentValues cv = new ContentValues();
        cv.put(DATE_NAME, cName);
        cv.put(DATE_TIME, dTime);
        cv.put(DATE, cDate);
        cv.put(COMFORT_LEVEL, cLevel);
        cv.put(ADDITIONAL_INFO, aInfo);

        this.openWriteableDB();
        long rowID = db.insert(DATES_TABLE, null, cv);
        this.closeDB();

    }

    public int updateDateStartTime(int id, String time){
        ContentValues cv = new ContentValues();
        cv.put(START_TIME, time);

        String where = DATE_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.update(DATES_TABLE, cv, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    //Insert start date timestamp
    public void insertStartTime(){
        ContentValues cv = new ContentValues();
        cv.put(START_TIME, "TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
        this.openWriteableDB();
        long rowId = db.insert(DATES_TABLE, null, cv);
        this.closeDB();
    }

    //Insert end date timestamp
    public void insertEndTime(){
        ContentValues cv = new ContentValues();
        cv.put(END_TIME, "TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
        this.openWriteableDB();
        long rowId = db.insert(DATES_TABLE, null, cv);
        this.closeDB();
    }

    /**
     * Gets all users from the database
     * @return ArrayList of users
     */
    public ArrayList<PlannedDate> getAllDates(){
        ArrayList<PlannedDate> dates = new ArrayList<>();

        this.openReadableDB();
        Cursor cur = db.rawQuery("SELECT * FROM " + DATES_TABLE, null);
        boolean exist = (cur.getCount() > 0);

        if(exist){
            while (cur.moveToNext()) {
                dates.add(getPlannedDateFromCursor(cur));
                Log.d(TAG, "Got a user");
            }
        }
        if (cur != null)
            cur.close();
        this.closeDB();
        return dates;
    }

    /**
     * Gets dates that do not have a start time
     * @return
     */
    public ArrayList<PlannedDate> getPlannedDates(){
        ArrayList<PlannedDate> dates = new ArrayList<>();
        this.openReadableDB();
        Cursor cur = db.rawQuery("SELECT * FROM " + DATES_TABLE + " WHERE "+START_TIME+" IS NULL", null);
        Log.d(TAG,"cursor count: "+cur.getCount());

        boolean exist = (cur.getCount() > 0);

        if(exist){
            while (cur.moveToNext()) {
                dates.add(getPlannedDateFromCursor(cur));
                Log.d(TAG, "Got a user");
            }
        }
        if (cur != null)
            cur.close();
        this.closeDB();
        return dates;
    }

    /**
     * Gets past dates, which are the ones that alreadu have a start time
     * @return
     */
    public ArrayList<PlannedDate> getPastDates(){
        ArrayList<PlannedDate> dates = new ArrayList<>();

        String where = START_TIME + "!= ?";
        String[] whereArgs = { "" };

        this.openReadableDB();
        Cursor cur = db.query(DATES_TABLE,
                null, where, whereArgs, null, null, null);
        Log.d(TAG,"cursor count: "+cur.getCount());

        boolean exist = (cur.getCount() > 0);
        if(exist){
            while (cur.moveToNext()) {
                Log.d(TAG, "inside while loop");
                dates.add(getPastDateFromCursor(cur));
                Log.d(TAG, "Got a user");
            }
        }
        if (cur != null)
            cur.close();
        this.closeDB();
        return dates;
    }

    //END USER METHODS
    //////////////////////////////////////////////////////////////////////////////////
    // CHAPERONES METHODS

    // Insert a chaperone into the database
    public void insertChaperone(String cName, String cNumber) {

        ContentValues cv = new ContentValues();
        cv.put(CHAPERONE_NAME, cName);
        cv.put(CHAPERONE_PHONE_NUMBER, cNumber);


        this.openWriteableDB();
        long rowID = db.insert(CHAPERONES_TABLE, null, cv);
        this.closeDB();

    }

    //select chaperone for date
    public List<Chaperones> chaperoneNameList (){
        List<Chaperones> chaperonesNameList = new ArrayList<>();
        String selectQuery = "SELECT " + CHAPERONE_NAME + " FROM " + CHAPERONES_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Chaperone chaperone = new Chaperone();
                chaperone.setChaperoneName(cursor.getString(0));
                //add to chaperone list
                chaperonesNameList.add(chaperone);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return chaperonesNameList;
    }

    //select chaperone ID
    public String selectChaperoneID(String cName) {
        this.openReadableDB();
        Cursor cID = db.rawQuery("SELECT " + CHAPERONE_ID + " FROM " + CHAPERONES_TABLE + " WHERE " + CHAPERONE_NAME + " = '" + cName + "'", null);
        cID.moveToFirst();
        if(cID.moveToFirst()){
            chaperoneID=cID.getString(cID.getColumnIndex("chaperoneID"));
        }
        this.closeDB();
        cID.close();
        return chaperoneID;
    }

    public ArrayList<Chaperone> getAllChaperonesFromDB(){
        ArrayList<Chaperone> chaps = new ArrayList<>();

        this.openReadableDB();
        Cursor cur = db.rawQuery("SELECT * FROM " + CHAPERONES_TABLE, null);
        boolean exist = (cur.getCount() > 0);
        Log.d(TAG, "count of cursor: "+cur.getCount());
        if(exist){
            while (cur.moveToNext()) {
                chaps.add(getChaperoneFromCursor(cur));
                //Log.d(TAG, "chap name: "+getChaperoneFromCursor(cur).getChaperoneName());
            }
        }
        if (cur != null)
            cur.close();
        this.closeDB();

        return chaps;
    }

    public int deleteChaperoneFromDB(int id){
            String where = CHAPERONE_ID + "= ?";
            String[] whereArgs = { String.valueOf(id) };

            this.openWriteableDB();
            int rowCount = db.delete(CHAPERONES_TABLE, where, whereArgs);
            this.closeDB();

            return rowCount;
    }
    //END CHAPERONES METHODS
    //////////////////////////////////////////////////////////////////////////////////
    // DATE CHAPERONES METHODS

    // Insert a date's chaperone into the database
    public void insertDateChaperones(String dName, String cID) {

        ContentValues cv = new ContentValues();
        cv.put(CHAPERONE_ID, cID);
        cv.put(DATE_NAME, dName);

        this.openWriteableDB();
        long rowID = db.insert(DATE_CHAPERONES_TABLE, null, cv);
        this.closeDB();

    }

    /**
     * Gets a User from the cursor
     * @param cursor
     * @return User
     */
    private Chaperone getChaperoneFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            Log.d(TAG, "cursor is null or count is 0");
            return null;
        }
        else {
            try {
                String name = cursor.getString(CHAPERONE_NAME_COL);
                String phone = cursor.getString(CHAPERONE_PHONE_NUMBER_COL);
                int id = cursor.getInt(CHAPERONE_ID_COL);
                Log.d(TAG, name);
                Log.d(TAG, phone);
                Log.d(TAG, ""+id);

                Chaperone chap = new Chaperone(name,phone, id);
                return chap;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    /**
     * Gets a User from the cursor
     * @param cursor
     * @return User
     */
    private PlannedDate getPlannedDateFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {

                int id =  cursor.getInt(DATE_ID_COL);
                String name =  cursor.getString(DATE_NAME_COL);
                String info =  cursor.getString(DATE_ADDITIONAL_INFO_COL);
                String date =  cursor.getString(DATE_COL);
                String time = cursor.getString(DATE_TIME_COL);

                PlannedDate pd = new PlannedDate(id, name, info, date, time);
                return pd;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    private PlannedDate getPastDateFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Log.d(TAG, "past date from cursor");
                int id =  cursor.getInt(DATE_ID_COL);
                String name =  cursor.getString(DATE_NAME_COL);
                String info =  cursor.getString(DATE_ADDITIONAL_INFO_COL);
                String date =  cursor.getString(DATE_COL);
                String time = cursor.getString(DATE_TIME_COL);
                PlannedDate pd = new PlannedDate(id, name, info, date, time);
                return pd;
            }
            catch(Exception e) {
                return null;
            }
        }
    }
}
