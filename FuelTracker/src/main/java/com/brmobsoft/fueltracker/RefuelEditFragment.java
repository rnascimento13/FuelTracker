package com.brmobsoft.fueltracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;

public class RefuelEditFragment extends Fragment {
    public static int currentItem;
    public static long currentItemID;
    public static EditText etRefuelDate;
    private static EditText etRefuelPay, etRefuelQuantity, etRefuelOdometer;
    private static TextView tvRefuelPayUnit, tvRefuelOdometerUnit;
    private static Spinner spRefuelType;
    private static long mRefuelDate;
    private static View view;
    private static FuelItem fuelItem = null;
    private static Calendar calendar;
    private static SharedPreferences prefs;
    private static String dateString, oldDateString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            calendar = Calendar.getInstance();
            currentItemID = getArguments().getLong(MyApp.CURRENT_ITEM_ID);
            currentItem = MyApp.fuelListAdapter.getItemPositionByID(currentItemID);
            if (currentItem == -1)fuelItem = new FuelItem(0,0,0,0,0,0);
            else fuelItem = MyApp.fuelListAdapter.getFuelItem(currentItem);
            calendar.setTimeInMillis(fuelItem.getDate());
            mRefuelDate = calendar.getTimeInMillis();
            dateString = MyApp.dateFormat.format(calendar.getTimeInMillis())+" "+ MyApp.timeFormat.format(calendar.getTimeInMillis());
            oldDateString = dateString;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        view = inflater.inflate(R.layout.refuel_save, container, false);
        initialize(view);
        setMyData();
        return view;
    }
    private void initialize(View v){
        try{
            etRefuelDate = (EditText) v.findViewById(R.id.etRefuelSaveDate);
            spRefuelType = (Spinner) v.findViewById(R.id.spRefuelSaveType);
            etRefuelPay = (EditText) v.findViewById(R.id.etRefuelSavePay);
            etRefuelQuantity = (EditText) v.findViewById(R.id.etRefuelSaveQuantity);
            etRefuelOdometer = (EditText) v.findViewById(R.id.etRefuelSaveOdometer);
            tvRefuelPayUnit = (TextView) v.findViewById(R.id.tvRefuelPayUnit);
            tvRefuelOdometerUnit = (TextView) v.findViewById(R.id.tvRefuelOdometerUnit);
        }catch(Exception erro){
        }
    }
    public void setMyData(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.tipo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRefuelType.setAdapter(adapter);
        spRefuelType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //DbHandler.mensagemExibir("Aviso", "OnItemSelected", getActivity());
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        etRefuelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog();
            }
        });

        //etRefuelDate.setText(dateString);
        etRefuelDate.setText(calendar.getTime().toString());

        spRefuelType.setSelection(fuelItem.getType());
        etRefuelQuantity.setText(String.valueOf(fuelItem.getQuantity()));
        etRefuelOdometer.setText(String.valueOf(fuelItem.getOdometer()));
        etRefuelPay.setText(String.valueOf(fuelItem.getPay()));
        tvRefuelPayUnit.setText(MyApp.ANDROID_CURRENCY);
        tvRefuelOdometerUnit.setText(prefs.getString("odometro", this.getString(R.string.tv_Refuel_Odometer_Unit_Kilometers)));
        getActivity().getActionBar().setSubtitle(null);
    }
    public static FuelItem getFuelItem(Activity activity) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Boolean isOKToSave = true;
        long quantity = 0, pay = 0, odometer = 0;
        try {
            quantity = NumberFormat.getInstance().parse(etRefuelQuantity.getText().toString()).longValue();
            pay = NumberFormat.getInstance().parse(etRefuelPay.getText().toString()).longValue();
            odometer = NumberFormat.getInstance().parse(etRefuelOdometer.getText().toString()).longValue();
        }
        catch(Exception e) { isOKToSave = false; } // false if not numeric
        long rowId = fuelItem.getId();
        int type = spRefuelType.getSelectedItemPosition();
        FuelItem myFuelItem = new FuelItem(rowId,mRefuelDate,type,quantity,pay,odometer);
        if((fuelItem.getType() == myFuelItem.getType())&&(fuelItem.getOdometer() == myFuelItem.getOdometer())&&
                (fuelItem.getPay() == myFuelItem.getPay())&&(fuelItem.getQuantity() == myFuelItem.getQuantity())&&
                (dateString.compareTo(oldDateString) == 0))
            Toast.makeText(activity, "Dados ja estão gravados com sucesso!", Toast.LENGTH_SHORT).show();
        else if(isOKToSave) {
            fuelItem = new FuelItem(rowId,mRefuelDate,type,quantity,pay,odometer);
            return fuelItem;
        } else Toast.makeText(activity, "Existem Campos Invalidos ou Vazios", Toast.LENGTH_SHORT).show();
        return null;
    }
    private void showDateTimeDialog() {
        final Dialog mDateTimeDialog = new Dialog(this.getActivity());
        final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater(Bundle.EMPTY).inflate(R.layout.date_time_dialog, null);
        final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
        final boolean is24h = android.text.format.DateFormat.is24HourFormat(getActivity());
        ((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDateTimePicker.clearFocus();
                dateString = MyApp.dateFormat.format(mDateTimePicker.getDateTimeMillis())+" "+ MyApp.timeFormat.format(mDateTimePicker.getDateTimeMillis());
                etRefuelDate.setText(dateString);
                mRefuelDate = mDateTimePicker.getDateTimeMillis();
                calendar.setTimeInMillis(mRefuelDate);
                mDateTimeDialog.dismiss();
            }
        });
        ((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDateTimeDialog.cancel();
            }
        });
        ((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDateTimePicker.reset();
                mDateTimePicker.clearFocus();
                dateString = MyApp.dateFormat.format(mDateTimePicker.getDateTimeMillis())+" "+ MyApp.timeFormat.format(mDateTimePicker.getDateTimeMillis());
                etRefuelDate.setText(dateString);
                mRefuelDate = mDateTimePicker.getDateTimeMillis();
                calendar.setTimeInMillis(mRefuelDate);
                mDateTimeDialog.dismiss();
            }
        });
        mDateTimePicker.setIs24HourView(is24h);
        mDateTimePicker.setDateTime(calendar.getTimeInMillis());
        mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDateTimeDialog.setContentView(mDateTimeDialogView);
        mDateTimeDialog.show();
    }
}

