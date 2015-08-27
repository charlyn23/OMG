package charlyn23.c4q.nyc.omg.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by c4q-ac35 on 8/1/15.
 */
public class ResourcesHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "locations.db";
    private static final int DB_VERSION = 1;

    private static ResourcesHelper INSTANCE;


    public static synchronized ResourcesHelper getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new ResourcesHelper(context.getApplicationContext());
        }

        return INSTANCE;
    }

    public ResourcesHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_SAFETY);
        db.execSQL(SQL_CREATE_TABLE_FOOD);
        db.execSQL(SQL_CREATE_TABLE_MISSING_PERSON);
        db.execSQL(SQL_CREATE_TABLE_SHELTER);
        db.execSQL(SQL_CREATE_TABLE_MONEY);
        db.execSQL(SQL_CREATE_TABLE_EMOTIONAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void insertData(String tableName){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(tableName, null, null);
    }

    public static void insertRow(String resourceName, String address, String phoneNumber, String hours, String tableName, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(ResourceEntry.COLUMN_OFFICE,resourceName);
        values.put(ResourceEntry.COLUMN_ADDRESS,address);
        values.put(ResourceEntry.COLUMN_PHONE_NUMBER,phoneNumber);
        values.put(ResourceEntry.COLUMN_HOURS,hours);


        db.insertOrThrow(tableName,null,values);
    }

    public static abstract class ResourceEntry implements BaseColumns{
        public static final String COLUMN_OFFICE = "Office";
        public static final String COLUMN_ADDRESS = "Address";
        public static final String COLUMN_PHONE_NUMBER = "Phone Number";
        public static final String COLUMN_HOURS = "Hours";
    }

    private static final String SQL_CREATE_TABLE_SAFETY =
            "CREATE TABLE" + "IMMEDIATE SAFETY" +" (" +
                    ResourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ResourceEntry.COLUMN_OFFICE+" TEXT," +
                    ResourceEntry.COLUMN_ADDRESS+" TEXT," +
                    ResourceEntry.COLUMN_PHONE_NUMBER+" TEXT," +
                    ResourceEntry.COLUMN_HOURS+" TEXT"+
                    " )";

    private static final String SQL_CREATE_TABLE_FOOD =
            "CREATE TABLE" + "FOOD BANKS" +" (" +
                    ResourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ResourceEntry.COLUMN_OFFICE+" TEXT," +
                    ResourceEntry.COLUMN_ADDRESS+" TEXT," +
                    ResourceEntry.COLUMN_PHONE_NUMBER+" TEXT," +
                    ResourceEntry.COLUMN_HOURS+" TEXT"+
                    " )";


    private static final String SQL_CREATE_TABLE_SHELTER =
            "CREATE TABLE" + "SHELTERS" +" (" +
                    ResourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ResourceEntry.COLUMN_OFFICE+" TEXT," +
                    ResourceEntry.COLUMN_ADDRESS+" TEXT," +
                    ResourceEntry.COLUMN_PHONE_NUMBER+" TEXT," +
                    ResourceEntry.COLUMN_HOURS+" TEXT"+
                    " )";

    private static final String SQL_CREATE_TABLE_MISSING_PERSON =
            "CREATE TABLE" + "MISSING PERSON" +" (" +
                    ResourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ResourceEntry.COLUMN_OFFICE+" TEXT," +
                    ResourceEntry.COLUMN_ADDRESS+" TEXT," +
                    ResourceEntry.COLUMN_PHONE_NUMBER+" TEXT," +
                    ResourceEntry.COLUMN_HOURS+" TEXT"+
                    " )";

    private static final String SQL_CREATE_TABLE_MONEY =
            "CREATE TABLE" + "MONEY RESOURCES" +" (" +
                    ResourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ResourceEntry.COLUMN_OFFICE+" TEXT," +
                    ResourceEntry.COLUMN_ADDRESS+" TEXT," +
                    ResourceEntry.COLUMN_PHONE_NUMBER+" TEXT," +
                    ResourceEntry.COLUMN_HOURS+" TEXT"+
                    " )";

    private static final String SQL_CREATE_TABLE_EMOTIONAL =
            "CREATE TABLE" + "EMOTIONAL HELP" +" (" +
                    ResourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ResourceEntry.COLUMN_OFFICE+" TEXT," +
                    ResourceEntry.COLUMN_ADDRESS+" TEXT," +
                    ResourceEntry.COLUMN_PHONE_NUMBER+" TEXT," +
                    ResourceEntry.COLUMN_HOURS+" TEXT"+
                    " )";
    public String deleteTable(String tableName){
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + tableName.toUpperCase();
        return SQL_DELETE_ENTRIES;
    }
}