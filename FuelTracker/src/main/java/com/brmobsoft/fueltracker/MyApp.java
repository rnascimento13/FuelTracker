/**
 * sBrowser
 * Copyright (C) Carles Sentis 2011 <codeskraps@gmail.com>
 *
 * sBrowser is free software: you can
 * redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later
 * version.
 *  
 * sBrowser is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *  
 * You should have received a copy of the GNU
 * General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.brmobsoft.fueltracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;
import java.util.Stack;

public class MyApp extends Application {
    public static final String REFUEL_ADD_FRAGMENT_TAG = "refuel_add_fragment";
    public static final String REFUEL_EDIT_FRAGMENT_TAG = "refuel_edit_fragment";
    public static final String REFUEL_LIST_FRAGMENT_TAG = "refuel_list_fragment";
    public static final String REFUEL_VIEW_FRAGMENT_TAG = "refuel_view_fragment";
    public static final String HOME_FRAGMENT_TAG = "home_fragment";
    public static final String NULL_FRAGMENT_TAG = "null_fragment";

    public static final int REFUEL_ADD_FRAGMENT_INT = -2;
    public static final int REFUEL_EDIT_FRAGMENT_INT = -3;
    public static final int REFUEL_LIST_FRAGMENT_INT = -4;
    public static final int REFUEL_VIEW_FRAGMENT_INT = -5;
    public static final int NULL_FRAGMENT_INT = -7;
    public static final int HOME_FRAGMENT_INT = -6;
    public static final int NO_NEW_ITEM_INT = -1;
    public static final int POSITION_NONE = -2;

    public static final long REFUEL_ADD_FRAGMENT_LONG = -2;
    public static final long REFUEL_LIST_FRAGMENT_LONG = -4;
    public static final long HOME_FRAGMENT_LONG = -6;
    public static final long NULL_FRAGMENT_LONG = -7;

    public static final String CURRENT_ITEM = "CURRENT_ITEM";
    public static final String CURRENT_ITEM_ID = "CURRENT_ITEM_ID";
    public static final String NO_BACK_STACK = "NO_BACK_STACK";

    public static int highlightedItemPosition = -1;
    public static String ANDROID_CURRENCY = null;
    public static final String DEFAULT_CURRENCY = "USD";
    public static String dateFormatPattern;
    public static String currencyString;
    public static DateFormat dateFormat, dateFormat2, timeFormat;
    public static FuelItem fuelItem;
    public static FuelTrackerActivity fuelTrackerActivity;
    public static FuelListAdapter fuelListAdapter = null;
    public static Menu myMenu = null;
    public static Stack<Long> stack = new Stack<Long>();

    private static Context mContext;
    private CurrencyHandler currencyHandler = null;
    private FuelTrackerDbHelper dbHelper;
    private static boolean[] myMenuItemArray = new boolean[7];
    SharedPreferences prefs;
    Preference x;
    @Override
	public void onCreate() {
		super.onCreate();
//        myMenu = null;
//        fuelListAdapter = null;
        mContext = this;
        setDbHelper(new FuelTrackerDbHelper(this));
        dbHelper.open();
        setCurrencyHandler(new CurrencyHandler(this));
        initializeSettings();
        initializeDateFormat();
    }
    @Override
	public void onTerminate() {
		super.onTerminate();
        dbHelper.close();
	}

    public FuelTrackerDbHelper getDbHelper() {
        return dbHelper;
    }

    public CurrencyHandler getCurrencyHandler() {
        return currencyHandler;
    }

    public void setDbHelper(FuelTrackerDbHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

    public void setCurrencyHandler(CurrencyHandler currencyHandler) {
        this.currencyHandler = currencyHandler;
    }

    public static Context getContext(){
        return mContext;
    }

    public void initializeSettings() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isAutoCurrencyPreference = prefs.getBoolean("auto_currency_preference", true);
        if( isAutoCurrencyPreference == true){
            Currency currency = Currency.getInstance(Locale.getDefault());
            currencyString = currency.toString();
            ANDROID_CURRENCY = currencyHandler.findCurrency(currency.toString());
        }else {
            ANDROID_CURRENCY = prefs.getString("currency_preference", String.valueOf(R.string.USD_Currency_name));
        }
    }
    public void initializeDateFormat() {
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        dateFormatPattern = "MMM dd";
        if (android.os.Build.VERSION.SDK_INT >= 18){
            dateFormatPattern = df.getBestDateTimePattern(Locale.getDefault(), dateFormatPattern);
        }
        dateFormat = df.getMediumDateFormat(getApplicationContext());
        timeFormat = df.getTimeFormat(getApplicationContext());
        dateFormat2 = df.getLongDateFormat(getApplicationContext());

    }

    public static void setMenu(boolean home, boolean list, boolean add, boolean settings, boolean delete, boolean edit, boolean save) {
        myMenuItemArray[0] = home;
        myMenuItemArray[1] = list;
        myMenuItemArray[2] = add;
        myMenuItemArray[3] = settings;
        myMenuItemArray[4] = delete;
        myMenuItemArray[5] = edit;
        myMenuItemArray[6] = save;
        updateMenu();
    }
    public static void updateMenu() {
        myMenu.getItem(0).setVisible(myMenuItemArray[0]);//home
        myMenu.getItem(1).setVisible(myMenuItemArray[1]);//list
        myMenu.getItem(2).setVisible(myMenuItemArray[2]);//add
        myMenu.getItem(3).setVisible(myMenuItemArray[3]);//settings
        myMenu.getItem(4).setVisible(myMenuItemArray[4]);//delete
        myMenu.getItem(5).setVisible(myMenuItemArray[5]);//edit
        myMenu.getItem(6).setVisible(myMenuItemArray[6]);//save
    }
    public void makeText(String string){
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

}



