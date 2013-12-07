package com.brmobsoft.fueltracker;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.Calendar;

/**
 * Created by Rodrigo on 30/09/13.
 */
public class GetDataAsyncTask extends AsyncTask<Void, Void, Void>{
    private Cursor myCursor = null;
    private android.text.format.DateFormat df = new android.text.format.DateFormat();
    private final long NOW =  Calendar.getInstance().getTimeInMillis();
    private static final long DAY = 86400000L, YEAR = 31470526000L;
    private long lDateBetween = 0, refuelDate;
    private static FuelListAdapter listItemAdapter = null;
    private Activity activity;
    private FuelTrackerDbHelper myDBHelper = null;
    public GetDataAsyncTask(Activity activity) {
        this.activity = activity;
    }
    @Override
    protected Void doInBackground(Void... params) {
        myDBHelper = ((MyApp) activity.getApplication()).getDbHelper();
        listItemAdapter = new FuelListAdapter(activity);
        myCursor = myDBHelper.queryAllRefuel();
        activity.startManagingCursor(myCursor);
        final int idColumnIndex = myCursor.getColumnIndex(FuelTrackerDbHelper.KEY_ROWID);
        final int dateColumnIndex = myCursor.getColumnIndex(FuelTrackerDbHelper.KEY_DATE);
        final int typeColumnIndex = myCursor.getColumnIndex(FuelTrackerDbHelper.KEY_TYPE);
        final int quantityColumnIndex = myCursor.getColumnIndex(FuelTrackerDbHelper.KEY_QUANTITY);
        final int payColumnIndex = myCursor.getColumnIndex(FuelTrackerDbHelper.KEY_PAY);
        final int odometerColumnIndex = myCursor.getColumnIndex(FuelTrackerDbHelper.KEY_ODOMETER);
        int id, type;
        long date, quantity, pay, odometer;
        String dateString;
        while (myCursor.moveToNext()) {
            id = myCursor.getInt(idColumnIndex);
            refuelDate = myCursor.getLong(dateColumnIndex);
            date = refuelDate;
            lDateBetween = NOW - date;
            if (lDateBetween < DAY) dateString = MyApp.timeFormat.format(date);
            else if (lDateBetween < YEAR) dateString = df.format(MyApp.dateFormatPattern,date).toString();
            else dateString = MyApp.dateFormat.format(date);
            type = myCursor.getInt(typeColumnIndex);
            quantity = myCursor.getLong(quantityColumnIndex);
            pay = myCursor.getLong(payColumnIndex);
            odometer = myCursor.getLong(odometerColumnIndex);
            FuelItem b = new FuelItem(dateString,date,type,quantity,pay,odometer);
            b.setId(id);
            listItemAdapter.addItem(b);
        }
        MyApp.fuelListAdapter = listItemAdapter;
        return null;
    }
}

