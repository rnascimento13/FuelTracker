package com.brmobsoft.fueltracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;


public class PrefsActivity extends PreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String TAG = PrefsActivity.class.getSimpleName();
    String[] currencyArray;
    Preference x;
    private CurrencyHandler currencyHandler = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.preferences);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        x = findPreference("odometro");
        x.setSummary(prefs.getString("odometro", null));
        setCurrencyPreference();
        x = findPreference("currency_preference");
        ListPreference listPref = (ListPreference)findPreference("currency_preference");
        currencyArray = getResources().getStringArray(R.array.currency);
        Arrays.sort(currencyArray);
        listPref.setEntries(currencyArray);
        listPref.setEntryValues(currencyArray);
//        listPref.setTitle("Title");
//        dialogBasedPrefCat.addPreference(listPref); Adding under the category
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals("odometro")) {
            x.setSummary(prefs.getString("odometro", null));
            //weight = Integer.parseInt((prefs.getString("weightPref", "120")));
        }
        if (key.equals("auto_currency_preference")) {
            setCurrencyPreference();
        }
        if (key.equals("currency_preference")) {
            setCurrencyPreference();
        }
    }

    public void setCurrencyPreference() {
//        PreferenceManager.setDefaultValues(this, "auto_currency_preference", Context.MODE_PRIVATE, R.layout.preferences, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        x = findPreference("currency_preference");
        if (prefs.getBoolean("auto_currency_preference", false) == false){
            x.setSelectable(true);
            MyApp.ANDROID_CURRENCY = prefs.getString("currency_preference", ""+R.string.USD_Currency_name);
        } else {
            currencyHandler = ((MyApp) getApplication()).getCurrencyHandler();
            Currency currency = Currency.getInstance(Locale.getDefault());
            MyApp.ANDROID_CURRENCY = currencyHandler.findCurrency(currency.toString());
            x = findPreference("currency_preference");
            x.setSelectable(false);
        }
    }
}
