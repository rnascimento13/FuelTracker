package com.brmobsoft.fueltracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;

public class FuelTrackerActivity extends FragmentActivity implements
        RefuelListFragment.OnListSelected, RefuelListFragment.OnListDelete {
    public static android.support.v4.app.FragmentManager myFM;
    public static boolean dualPanel;

    private static int auxFragmentID, fragmentID;
    private static long timeToExit;
    private static FuelTrackerDbHelper mDbHelper;
/**Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mPlanetTitles;
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) MyApp.myMenu = null;
        myFM = getSupportFragmentManager();
        MyApp.fuelTrackerActivity = this;
        mDbHelper = ((MyApp) getApplication()).getDbHelper();
        mDbHelper.open();
        setContentView(R.layout.main);

/**Navigation Drawer
        setNavDrawer(); // Navigation Drawer
*/
        //if (MyApp.fuelListAdapter == null) new GetDataAsyncTask(this).execute();//        getData();
        if (MyApp.fuelListAdapter == null) updateListAdapter(MyApp.NO_NEW_ITEM_INT);//        getData();
        checkDualPanel();
        if ( (savedInstanceState == null) || (dualPanel) ) {//on Dual Panel RefuelListFragment shows no Left
            final FragmentTransaction ft = myFM.beginTransaction();
            if (dualPanel) ft.replace(auxFragmentID, new RefuelListFragment(), MyApp.REFUEL_LIST_FRAGMENT_TAG);
            if (savedInstanceState == null) ft.replace(fragmentID, new HomeFragment(), MyApp.HOME_FRAGMENT_TAG);
            ft.commit();
            //if ( (savedInstanceState != null) && (dualPanel) ) FuelTrackerActivity.setListItemChecked(); // on DualPanel set selected on list on the lef

        }
        if ( (dualPanel) && (savedInstanceState != null) ) { //on DualPanel RefuelListFragment do not show on Right
            if (myFM.findFragmentById(R.id.myfragment).getTag() == MyApp.REFUEL_LIST_FRAGMENT_TAG){
                super.onBackPressed();
            }
        }
    }
/**Navigation Drawer
    private void setNavDrawer(){
        mPlanetTitles = getResources().getStringArray(R.array.nav_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle("Drawer Closed");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Drawer Open");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //if (savedInstanceState == null) selectItem(0);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener { // Navigation Drawer
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    private void selectItem(int position) { // Navigation Drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
*/
    @Override
    public void onBackPressed(){
        int _BackCount = myFM.getBackStackEntryCount();
        final String _FragmentTag = myFM.findFragmentById(fragmentID).getTag();
        if ( finishApp() ) return;
        String _BackString = myFM.getBackStackEntryAt(_BackCount - 1).getName();
        long _StackPeek;
        Boolean loop = true;
        while (loop) {
            _StackPeek = MyApp.stack.peek();
            if (!MyApp.fuelListAdapter.isIDValid(_StackPeek)) {
                MyApp.stack.pop();
                myFM.popBackStackImmediate();
            } else {
                _BackCount = myFM.getBackStackEntryCount();
                _BackString = myFM.getBackStackEntryAt(_BackCount - 1).getName();
                loop = false;
            }
        }
        _StackPeek = MyApp.stack.peek();
        if ( _BackCount == 0 ) finishApp(); // test again for finishApp
        else if ( (_StackPeek >= 0) && (_FragmentTag == MyApp.REFUEL_VIEW_FRAGMENT_TAG) ) { // if will show pageView and is already showing just pop and setCurrentItem
            final long _NewID = MyApp.stack.lastElement();
            MyApp.stack.pop();
            myFM.popBackStackImmediate();
            RefuelViewPagerFragment.setVP(MyApp.fuelListAdapter.getItemPositionByID(_NewID), true);
        } else if ( (_StackPeek >= 0) && (_BackString == MyApp.REFUEL_VIEW_FRAGMENT_TAG) ) { // if will show pageView and is not currently showing must load viewPagerFragment
            MyApp.stack.pop();
//            super.onBackPressed(); // Do the Back Press
            myFM.popBackStackImmediate();
            showRefuelView(MyApp.fuelListAdapter.getItemPositionByID(_StackPeek),_StackPeek,MyApp.NULL_FRAGMENT_LONG);
            RefuelViewPagerFragment.currentItem = RefuelListFragment.myListAdapter.getItemPositionByID(_StackPeek); // to avoid bug that highLight the previous item instead current
        } else {
            MyApp.stack.pop();
            super.onBackPressed(); // Do the Back Press
            updateMenu();

            //TODO quando volta para edit_fragment primeiro da erro no icone do menu e na proxima vez insiste em nao mudar o highlight
        }
        if (dualPanel) { // highLight correct Item on List when dualPanel
            if ((_BackString == MyApp.REFUEL_VIEW_FRAGMENT_TAG)||(_BackString == MyApp.REFUEL_EDIT_FRAGMENT_TAG))
            MyApp.highlightedItemPosition = RefuelListFragment.myListAdapter.getItemPositionByID(_StackPeek); // to avoid bug that highLight the previous item instead current
            else  MyApp.highlightedItemPosition = -1;
            RefuelListFragment.myListAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        MyApp.myMenu = menu;
        updateMenu();
        return super.onCreateOptionsMenu(menu);
    }
/**Navigation Drawer
    @Override
    protected void onPostCreate(Bundle savedInstanceState) { // Navigation Drawer
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();// Sync the toggle state after onRestoreInstanceState has occurred.
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) { // Navigation Drawer
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);// Pass any configuration change to the drawer toggls
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
/**Navigation Drawer
        if (mDrawerToggle.onOptionsItemSelected(item)) {// Navigation Drawer
            return true;
        }
*/
        FragmentTransaction ft = myFM.beginTransaction();
        switch (item.getItemId()) {
            case R.id.menu_Home:
                updateMenu(MyApp.HOME_FRAGMENT_TAG);
                MyApp.stack.push(backStackLong());
                ft.addToBackStack(backStackString())
                        .replace(fragmentID, new HomeFragment(), MyApp.HOME_FRAGMENT_TAG).commit();
                return (true);
            case R.id.menu_List:
                updateMenu(MyApp.REFUEL_LIST_FRAGMENT_TAG);
                MyApp.stack.push(backStackLong());
                ft.addToBackStack(backStackString())
                        .replace(fragmentID, new RefuelListFragment(), MyApp.REFUEL_LIST_FRAGMENT_TAG).commit();
                return (true);
            case R.id.menu_Add:
                MyApp.fuelItem = null;
                updateMenu(MyApp.REFUEL_ADD_FRAGMENT_TAG);
                MyApp.stack.push(backStackLong());
                ft.addToBackStack(backStackString())
                        .replace(fragmentID, new RefuelAddFragment(), MyApp.REFUEL_ADD_FRAGMENT_TAG).commit();
                return (true);
            case R.id.menu_Settings:
                startActivity(new Intent(this, PrefsActivity.class));
                return (true);
            case R.id.menu_RefuelEdit:
                final Fragment newFragment = new RefuelEditFragment();
                final Bundle newFragmentBundle = new Bundle();
                //Toast.makeText(this, "Edit "+RefuelViewPagerFragment.currentItemID, Toast.LENGTH_SHORT).show();
                newFragmentBundle.putLong(MyApp.CURRENT_ITEM_ID, RefuelViewPagerFragment.currentItemID);
                newFragment.setArguments(newFragmentBundle);
                updateMenu(MyApp.REFUEL_EDIT_FRAGMENT_TAG);
                MyApp.stack.push(backStackLong());
                ft.addToBackStack(backStackString())
                        .replace(fragmentID, newFragment, MyApp.REFUEL_EDIT_FRAGMENT_TAG).commit();
                return (true);
            case R.id.menu_refuelSave:
                final String mTag = myFM.findFragmentById(fragmentID).getTag();
                if (mTag == MyApp.REFUEL_ADD_FRAGMENT_TAG) { // if you just save on the add fragment
                    final FuelItem mFuelItem = RefuelAddFragment.getFuelItem(this);
                    if (mFuelItem != null ) {
                        final long mID = mDbHelper.createRefuel(mFuelItem);
                        final int newItem = updateListAdapter(mID);
                        showRefuelView(newItem,-1, MyApp.NULL_FRAGMENT_LONG);
                        if (dualPanel) ft.replace(auxFragmentID, new RefuelListFragment(), MyApp.REFUEL_LIST_FRAGMENT_TAG).commit();
                    }
                } else if (mTag == MyApp.REFUEL_EDIT_FRAGMENT_TAG) { // if you just save on edit fragment
                    final FuelItem mFuelItem = RefuelEditFragment.getFuelItem(this); // check if is ok to save the edit
                    if (mFuelItem != null ) {
                        mDbHelper.updateRefuel(mFuelItem); // update DB
                        updateListAdapter(MyApp.NO_NEW_ITEM_INT); // update the List
                        if (dualPanel) ft.replace(auxFragmentID, new RefuelListFragment(), MyApp.REFUEL_LIST_FRAGMENT_TAG).commit();
                        MyApp.stack.pop(); // delete last item on Stack
                        myFM.popBackStackImmediate(); // delete last item on BackStack
                        showRefuelView(-1,mFuelItem.getId(), MyApp.NULL_FRAGMENT_LONG);
                    }
                }
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }
    @Override
    public void onListSelected(int data) {
//        if (data == 0) showStack(); // debug to show the stack
//        else
            showRefuelView(data,-1, backStackLong());
    }
    @Override
    public void onListDelete() {
        final SparseBooleanArray selected = MyApp.fuelListAdapter.getSelectedIds();
        final FragmentTransaction fT = myFM.beginTransaction();
        String fString = myFM.findFragmentById(fragmentID).getTag();
        FuelItem selectedFuelItem;
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                selectedFuelItem = MyApp.fuelListAdapter.getFuelItem(selected.keyAt(i));
                if ( (fString == MyApp.REFUEL_EDIT_FRAGMENT_TAG) && (RefuelEditFragment.currentItemID == selectedFuelItem.getId()) ){
                    MyApp.stack.push(MyApp.NULL_FRAGMENT_LONG);
                    fT.addToBackStack(MyApp.NULL_FRAGMENT_TAG)
                            .replace(fragmentID, new HomeFragment(), MyApp.HOME_FRAGMENT_TAG);
                    updateMenu(MyApp.HOME_FRAGMENT_TAG);
                    fString = MyApp.HOME_FRAGMENT_TAG;
                }
                if ( (fString == MyApp.REFUEL_VIEW_FRAGMENT_TAG) && (RefuelViewPagerFragment.currentItemID == selectedFuelItem.getId()) ){
                    MyApp.stack.push(MyApp.NULL_FRAGMENT_LONG);
                    fT.addToBackStack(MyApp.NULL_FRAGMENT_TAG)
                            .replace(fragmentID, new HomeFragment(), MyApp.HOME_FRAGMENT_TAG);
                    updateMenu(MyApp.HOME_FRAGMENT_TAG);
                    fString = MyApp.HOME_FRAGMENT_TAG;
                }
                mDbHelper.deleteRefuel(selectedFuelItem.getId());
            }
        }
        final long newItemID = RefuelViewPagerFragment.currentItemID;
        updateListAdapter(MyApp.NO_NEW_ITEM_INT);
        if (fString == MyApp.REFUEL_LIST_FRAGMENT_TAG) {
            MyApp.stack.push(MyApp.NULL_FRAGMENT_LONG);
            fT.addToBackStack(MyApp.NULL_FRAGMENT_TAG)
                    .replace(fragmentID, new RefuelListFragment(), MyApp.REFUEL_LIST_FRAGMENT_TAG);
        }
        if (fString == MyApp.REFUEL_VIEW_FRAGMENT_TAG) {
            showRefuelView(MyApp.fuelListAdapter.getItemPositionByID(newItemID),newItemID,MyApp.NULL_FRAGMENT_LONG);
        }
        if (dualPanel) fT.replace(auxFragmentID, new RefuelListFragment(), MyApp.REFUEL_LIST_FRAGMENT_TAG);
        fT.commit();
        RefuelViewPagerFragment.mFragmentStatePagerAdapter.notifyDataSetChanged();
    }
    private void showRefuelView(int data,long newID, long oldID) {
        final String _FString = myFM.findFragmentById(fragmentID).getTag();
        if ( (_FString == MyApp.REFUEL_VIEW_FRAGMENT_TAG) && (newID < 0)) RefuelViewPagerFragment.setVP(data, false);
        else {
            final RefuelViewPagerFragment newFragment = new RefuelViewPagerFragment();
            final Bundle newFragmentBundle = new Bundle();
            if (newID < 0) newID = MyApp.fuelListAdapter.getFuelItemID(data);
            newFragmentBundle.putLong(MyApp.CURRENT_ITEM_ID, newID);
            newFragment.setArguments(newFragmentBundle);
            MyApp.stack.push(oldID);
            myFM.beginTransaction().addToBackStack(_FString)
                    .replace(fragmentID, newFragment, MyApp.REFUEL_VIEW_FRAGMENT_TAG).commit();
            updateMenu();
        }
    }
    private int updateListAdapter(long mID) { // query dataBase and update listAdapter
        final Cursor myCursor;
        final android.text.format.DateFormat df = new android.text.format.DateFormat();
        final FuelListAdapter listItemAdapter;
        final FuelTrackerDbHelper myDBHelper;
        final long NOW =  Calendar.getInstance().getTimeInMillis();
        final long DAY = 86400000L, YEAR = 31470526000L;
        long lDateBetween, refuelDate;
        int newID = -1;
        myDBHelper = ((MyApp) getApplication()).getDbHelper();
        listItemAdapter = new FuelListAdapter(this);
        myCursor = myDBHelper.queryAllRefuel();
        this.startManagingCursor(myCursor);
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
            if ( b.getId() == (int) mID ) newID = myCursor.getCount()-1; // find newID when add
        }
        MyApp.fuelListAdapter = listItemAdapter;
        return newID;
    }
    private long backStackLong(){
        final String mFString = myFM.findFragmentById(fragmentID).getTag();
        if ( mFString == MyApp.HOME_FRAGMENT_TAG ) return MyApp.HOME_FRAGMENT_LONG;
        else if ( mFString == MyApp.REFUEL_LIST_FRAGMENT_TAG ) return MyApp.REFUEL_LIST_FRAGMENT_LONG;
        else if ( mFString == MyApp.REFUEL_VIEW_FRAGMENT_TAG ) return RefuelViewPagerFragment.currentItemID;
        else if ( mFString == MyApp.REFUEL_EDIT_FRAGMENT_TAG ) return RefuelViewPagerFragment.currentItemID;
        else if ( mFString == MyApp.REFUEL_ADD_FRAGMENT_TAG ) return MyApp.REFUEL_ADD_FRAGMENT_LONG;
        return MyApp.NULL_FRAGMENT_LONG;
    }
    private String backStackString(){
        final String mFString = myFM.findFragmentById(fragmentID).getTag();
        if ( mFString == MyApp.HOME_FRAGMENT_TAG ) return MyApp.HOME_FRAGMENT_TAG;
        else if ( mFString == MyApp.REFUEL_LIST_FRAGMENT_TAG ) return MyApp.REFUEL_LIST_FRAGMENT_TAG;
        else if ( mFString == MyApp.REFUEL_VIEW_FRAGMENT_TAG ) return MyApp.REFUEL_VIEW_FRAGMENT_TAG;
        else if ( mFString == MyApp.REFUEL_EDIT_FRAGMENT_TAG ) return MyApp.REFUEL_EDIT_FRAGMENT_TAG;
        else if ( mFString == MyApp.REFUEL_ADD_FRAGMENT_TAG ) return MyApp.REFUEL_ADD_FRAGMENT_TAG;
        return MyApp.NULL_FRAGMENT_TAG;
    }
    private void updateMenu() { updateMenu(myFM.findFragmentById(fragmentID).getTag()); }
    private void updateMenu(String mFString){
        if ( (mFString == MyApp.HOME_FRAGMENT_TAG) && !dualPanel ) MyApp.setMenu(false, true, true, true, false, false, false);
        else if ( mFString == MyApp.HOME_FRAGMENT_TAG ) MyApp.setMenu(false, false, true, true, false, false, false);
        else if ( (mFString == MyApp.REFUEL_LIST_FRAGMENT_TAG) && !dualPanel ) MyApp.setMenu(true, false, true, true, false, false, false);
        else if ( mFString == MyApp.REFUEL_LIST_FRAGMENT_TAG ) MyApp.setMenu(true, false, false, true, false, false, false);
        else if ( mFString == MyApp.REFUEL_VIEW_FRAGMENT_TAG ) MyApp.setMenu(false, false, false, false, false, true, false);
        else if ( mFString == MyApp.REFUEL_EDIT_FRAGMENT_TAG ) MyApp.setMenu(false, false, false, false, false, false, true);
        else if ( mFString == MyApp.REFUEL_ADD_FRAGMENT_TAG ) MyApp.setMenu(false, false, false, false, false, false, true);
    }
    private void checkDualPanel() { // Check if fragment 2 exist if so set dual Panel or not
        fragmentID = R.id.myfragment;
        final View myFragment2 = findViewById(R.id.myfragment2);
        if (myFragment2 != null) {
            auxFragmentID = R.id.myfragment2;
            dualPanel = true;
        } else {
            auxFragmentID = 0;
            dualPanel = false;
        }
    }
    public static void setHighlightedItem(){
        final String _FString = myFM.findFragmentById(fragmentID).getTag();
        if (_FString == MyApp.REFUEL_VIEW_FRAGMENT_TAG) MyApp.highlightedItemPosition = RefuelViewPagerFragment.currentItem;
        //if (_FString == MyApp.REFUEL_VIEW_FRAGMENT_TAG) MyApp.highlightedItemPosition = RefuelListFragment.myListAdapter.getItemPositionByID(RefuelViewPagerFragment.currentItemID);

        //else if (_FString == MyApp.REFUEL_EDIT_FRAGMENT_TAG) MyApp.highlightedItemPosition = RefuelEditFragment.currentItem;
        else if (_FString == MyApp.REFUEL_EDIT_FRAGMENT_TAG) MyApp.highlightedItemPosition = MyApp.fuelListAdapter.getItemPositionByID(RefuelEditFragment.currentItemID);
        else MyApp.highlightedItemPosition = -1;
    }
    private boolean finishApp() {
        if (myFM.getBackStackEntryCount() == 0) {
            if (timeToExit > (Calendar.getInstance().getTimeInMillis()-3000)){
                mDbHelper.close();
                finish();
            } else {
                Toast.makeText(this, "Press Back again to Exit!", Toast.LENGTH_SHORT).show();
                timeToExit = Calendar.getInstance().getTimeInMillis();
            }
            return true;
        }
        return false;
    }
    private void showStack() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Stack");
        String backEntry;
        int backCount = MyApp.stack.size();
        int backCount2 = myFM.getBackStackEntryCount();
        final String mFString = myFM.findFragmentById(fragmentID).getTag();
        String myStack = "Actual Fragment "+mFString+" \n Stack-"+backCount+" BackStack-"+backCount2;
        for (int i = (backCount - 1); i >= 0; i--) {
            backEntry = myFM.getBackStackEntryAt(i).getName();
            myStack = myStack+" \n "+i+"-"+backEntry;
            backEntry = Long.toString(MyApp.stack.get(i));
            myStack = myStack+" "+backEntry;
        }
        alertDialog.setMessage(myStack);
        alertDialog.show();
    }
}
