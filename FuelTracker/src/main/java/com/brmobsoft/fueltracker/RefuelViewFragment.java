package com.brmobsoft.fueltracker;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RefuelViewFragment extends Fragment {
    public static int currentItem;
    public static long currentItemID;

    private static TextView tvRefuelDate, tvRefuelType, tvRefuelPay, tvRefuelQuantity, tvRefuelOdometer;
    private static View v;
    private static FuelItem fuelItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentItemID = getArguments().getLong(MyApp.CURRENT_ITEM_ID);

//        Toast.makeText(this.getActivity(), " "+currentItemID, Toast.LENGTH_LONG).show();

        currentItem = MyApp.fuelListAdapter.getItemPositionByID(currentItemID);
        if (currentItem == -1)fuelItem = new FuelItem(0,0,0,0,0,0);
        else fuelItem = MyApp.fuelListAdapter.getFuelItem(currentItem);


        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.refuel_view, container, false);
        initialize(v);
        setRefuelViewFragmentData();

        return v;
    }
    public void initialize(View v){
        try{
            tvRefuelDate = (TextView) v.findViewById(R.id.tvRefuelViewDate);
            tvRefuelType = (TextView) v.findViewById(R.id.tvRefuelViewType);
            tvRefuelPay = (TextView) v.findViewById(R.id.tvRefuelViewPay);
            tvRefuelQuantity = (TextView) v.findViewById(R.id.tvRefuelViewQuantity);
            tvRefuelOdometer = (TextView) v.findViewById(R.id.tvRefuelViewOdometer);
            getActivity().getActionBar().setSubtitle(null);
        }catch(Exception erro){
        }
    }
    public void setRefuelViewFragmentData(){
        Resources res = getResources();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(fuelItem.getDate());
        tvRefuelDate.setText(MyApp.dateFormat2.format(c.getTimeInMillis())+
                " "+ MyApp.timeFormat.format(c.getTimeInMillis()));
        if (fuelItem.getType() == 1) tvRefuelType.setText(R.string.FuelType1);
        else tvRefuelType.setText(R.string.FuelType2);
        tvRefuelQuantity.setText(fuelItem.getQuantity() + " " + res.getString(R.string.FuelQuantityUnit));
        tvRefuelOdometer.setText(fuelItem.getOdometer() + " " + res.getString(R.string.FuelOdometerUnit));
        tvRefuelPay.setText(fuelItem.getPay() + " " + res.getString(R.string.FuelPayUnit));
    }
}
