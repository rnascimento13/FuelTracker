package com.brmobsoft.fueltracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class RefuelAddFragment extends Fragment {
    public static EditText etRefuelDate;
    private static EditText etRefuelPay, etRefuelQuantity, etRefuelOdometer;
    private static TextView tvRefuelPayUnit, tvRefuelOdometerUnit;
    private static Spinner spRefuelType;
    private static View view;
    private static Calendar calendar = Calendar.getInstance();
    private static SharedPreferences prefs;
    //private FuelItem fuelItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        }catch(Exception error){
        }
    }
    private void setMyData(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this.getActivity(), R.array.tipo,R.layout.spinner_center_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRefuelType.setAdapter(adapter);
        spRefuelType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
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
        etRefuelDate.setText(MyApp.dateFormat.format(calendar.getTimeInMillis()) +
                " " + MyApp.timeFormat.format(calendar.getTimeInMillis()));
        tvRefuelPayUnit.setText(MyApp.ANDROID_CURRENCY);
        tvRefuelOdometerUnit.setText(prefs.getString("odometro", this.getString(R.string.tv_Refuel_Odometer_Unit_Kilometers)));
        getActivity().getActionBar().setSubtitle(null);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
//        display.getSize(size);
//        float width = (float)size.x;
//        float height = (float)size.y;
//        width = convertPixelsToDp(width,getActivity().getApplicationContext());
//        height = convertPixelsToDp(height,getActivity().getApplicationContext());

//        etRefuelOdometer.setText("Width "+width+" Height "+height);


    }
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
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
        int type = spRefuelType.getSelectedItemPosition();
        if(isOKToSave) {
            return new FuelItem(calendar.getTimeInMillis(),type,quantity,pay,odometer);
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
//                mDateTimePicker.clearFocus();
                etRefuelDate.setText(MyApp.dateFormat.format(mDateTimePicker.getDateTimeMillis())+
                        " "+ MyApp.timeFormat.format(mDateTimePicker.getDateTimeMillis()));
                calendar.setTimeInMillis(mDateTimePicker.getDateTimeMillis());
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
//                mDateTimePicker.reset();
//                mDateTimePicker.clearFocus();
                etRefuelDate.setText(MyApp.dateFormat.format(mDateTimePicker.getDateTimeMillis())+
                        " "+ MyApp.timeFormat.format(mDateTimePicker.getDateTimeMillis()));
                calendar.setTimeInMillis(mDateTimePicker.getDateTimeMillis());
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
