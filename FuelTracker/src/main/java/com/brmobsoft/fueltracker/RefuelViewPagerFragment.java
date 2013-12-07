package com.brmobsoft.fueltracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RefuelViewPagerFragment extends Fragment {
    public static ViewPager pager;
    public static FuelListAdapter fuelListAdapter;
    public static FuelPageAdapter mFragmentStatePagerAdapter;
    public static int currentItem;
    public static long currentItemID;
    public static boolean noBackStack = true;
    private static boolean wasNotSelected = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentStatePagerAdapter = new FuelPageAdapter(getChildFragmentManager());
        fuelListAdapter = MyApp.fuelListAdapter;
        currentItemID = getArguments().getLong(MyApp.CURRENT_ITEM_ID);
        noBackStack = true; // so never backStack when create
        currentItem = fuelListAdapter.getItemPositionByID(currentItemID);
        final View v = inflater.inflate(R.layout.refuel_viewpager, container, false);
        pager = (ViewPager) v.findViewById(R.id.pager);
        wasNotSelected = true;
        pager.setAdapter(mFragmentStatePagerAdapter);
        pager.setCurrentItem(currentItem);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) noBackStack = false;
            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            public void onPageSelected(int position) {
                if (currentItem != position) {
                    if(!noBackStack) {
                        MyApp.stack.push(fuelListAdapter.getFuelItemID(currentItem));
                        FuelTrackerActivity.myFM.beginTransaction().addToBackStack(MyApp.REFUEL_VIEW_FRAGMENT_TAG).commit();
                    }
                    currentItem = position;
                    FuelTrackerActivity.setHighlightedItem();
                    if(FuelTrackerActivity.dualPanel) RefuelListFragment.myListAdapter.notifyDataSetChanged();
                }
                noBackStack = wasNotSelected = false;
                currentItemID = fuelListAdapter.getFuelItemID(position);

            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FuelTrackerActivity.setHighlightedItem();
        if(FuelTrackerActivity.dualPanel) RefuelListFragment.myListAdapter.notifyDataSetChanged();
    }

    public static void setVP(int _Position, Boolean _NoBackStack){
        noBackStack = _NoBackStack;
        pager.setCurrentItem(_Position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!wasNotSelected) noBackStack = true;
    }

    public class FuelPageAdapter extends FragmentStatePagerAdapter {
        public FuelPageAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            RefuelViewFragment newFragment = new RefuelViewFragment();
            Bundle newFragmentBundle = new Bundle();
            newFragmentBundle.putLong(MyApp.CURRENT_ITEM_ID, fuelListAdapter.getFuelItem(i).getId());
            newFragment.setArguments(newFragmentBundle);
            return newFragment;
        }
        @Override
        public int getCount() {
            return fuelListAdapter.getCount();
        }
    }
}
