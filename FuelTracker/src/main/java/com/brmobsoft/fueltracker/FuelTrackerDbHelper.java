package com.brmobsoft.fueltracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FuelTrackerDbHelper {
    public static final String KEY_DATE = "date";
    public static final String KEY_TYPE = "type";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_PAY = "pay";
    public static final String KEY_ODOMETER = "odometer";
    public static final String KEY_ROWID = "_id";
    private static final String TAG = "FuelTrackerDbHelper";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String INJECTDATA = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1063347796666,1,1,10,100);";
    private static final String INJECTDATA2 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1064347796666,0,2,20,200);";
    private static final String INJECTDATA3 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1065347796666,1,3,30,300);";
    private static final String INJECTDATA4 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1066347796666,0,4,40,400);";
    private static final String INJECTDATA5 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1067347796666,1,5,50,500);";
    private static final String INJECTDATA6 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1068347796666,0,6,60,600);";
    private static final String INJECTDATA7 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1069347796666,1,7,70,700);";
    private static final String INJECTDATA8 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1070347796666,0,8,80,800);";
    private static final String INJECTDATA9 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1071347796666,1,9,90,900);";
    private static final String INJECTDATA10 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1072347796666,0,10,100,1000);";
    private static final String INJECTDATA11 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1073347796666,1,11,110,1100);";
    private static final String INJECTDATA12 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1074347796666,0,12,120,1200);";
    private static final String INJECTDATA13 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1075347796666,1,13,130,1300);";
    private static final String INJECTDATA14 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1076347796666,0,14,140,1400);";
    private static final String INJECTDATA15 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1077347796666,1,15,150,1500);";
    private static final String INJECTDATA16 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1078347796666,0,16,160,1600);";
    private static final String INJECTDATA17 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1079347796666,1,17,170,1700);";
    private static final String INJECTDATA18 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1080347796666,0,18,180,1800);";
    private static final String INJECTDATA19 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1081347796666,1,19,190,1900);";
    private static final String INJECTDATA20 = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1082347796666,0,20,200,2000);";

    //private static final String INJECTDATA = " INSERT INTO refuel (date,type,quantity,pay,odometer) VALUES (1063347796666,1,1,1,1);";

    private static final String DATABASE_CREATE =
        "create table refuel (_id integer primary key autoincrement, " +
                KEY_DATE + " int not null, " +             //Date
                KEY_TYPE + " int not null, " +         //Type
                KEY_QUANTITY + " long not null, " +    //Quantity
                KEY_PAY + " long not null, " +         //Pay
                KEY_ODOMETER + " long not null);";      //Odometer
    private static final String DATABASE_NAME = "FuelTrackerDB";
    private static final String DATABASE_TABLE = "refuel";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
                db.execSQL(INJECTDATA);
                db.execSQL(INJECTDATA2);
                db.execSQL(INJECTDATA3);
                db.execSQL(INJECTDATA4);
                db.execSQL(INJECTDATA5);
                db.execSQL(INJECTDATA6);
                db.execSQL(INJECTDATA7);
                db.execSQL(INJECTDATA8);
                db.execSQL(INJECTDATA9);
                db.execSQL(INJECTDATA10);
                db.execSQL(INJECTDATA11);
                db.execSQL(INJECTDATA12);
                db.execSQL(INJECTDATA13);
                db.execSQL(INJECTDATA14);
                db.execSQL(INJECTDATA15);
                db.execSQL(INJECTDATA16);
                db.execSQL(INJECTDATA17);
                db.execSQL(INJECTDATA18);
                db.execSQL(INJECTDATA19);
                db.execSQL(INJECTDATA20);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS refuel");
            onCreate(db);
        }
    }
    /** Constructor - takes the context to allow the database to be opened/created */
    public FuelTrackerDbHelper(Context ctx) {
        this.mCtx = ctx;
    }
    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws android.database.SQLException if the database could be neither opened or created
     */
    public FuelTrackerDbHelper open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        mDbHelper.close();
    }
    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     *
     * @return rowId or -1 if failed
     */
    public long createRefuel(FuelItem fuelItem) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATE, fuelItem.getDate());
        initialValues.put(KEY_TYPE, fuelItem.getType());
        initialValues.put(KEY_PAY, fuelItem.getPay());
        initialValues.put(KEY_QUANTITY, fuelItem.getQuantity());
        initialValues.put(KEY_ODOMETER, fuelItem.getOdometer());
        return mDb.insert(DATABASE_TABLE, null, initialValues);

    }
    public long createRefuel(long date, int type, long pay, long quantity, long odometer) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_PAY, pay);
        initialValues.put(KEY_QUANTITY, quantity);
        initialValues.put(KEY_ODOMETER, odometer);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteRefuel(long rowId) {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    public Cursor queryAllRefuel() {
        return mDb.query(DATABASE_TABLE,
                new String[] {KEY_ROWID, KEY_DATE, KEY_TYPE, KEY_PAY, KEY_QUANTITY, KEY_ODOMETER},
                null, null, null, null, KEY_DATE);
    }

    public Cursor queryRefuel(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(DATABASE_TABLE,
                new String[] {KEY_ROWID, KEY_DATE, KEY_TYPE, KEY_PAY, KEY_QUANTITY, KEY_ODOMETER},
                KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public boolean updateRefuel(FuelItem fuelItem) {
        ContentValues args = new ContentValues();
        args.put(KEY_DATE, fuelItem.getDate());
        args.put(KEY_TYPE, fuelItem.getType());
        args.put(KEY_PAY, fuelItem.getPay());
        args.put(KEY_QUANTITY, fuelItem.getQuantity());
        args.put(KEY_ODOMETER, fuelItem.getOdometer());
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + fuelItem.getId(), null) > 0;
    }
    public boolean updateRefuel(long rowId, long date, int type, long pay, long quantity, long odometer) {
        ContentValues args = new ContentValues();
        args.put(KEY_DATE, date);
        args.put(KEY_TYPE, type);
        args.put(KEY_PAY, pay);
        args.put(KEY_QUANTITY, quantity);
        args.put(KEY_ODOMETER, odometer);
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
