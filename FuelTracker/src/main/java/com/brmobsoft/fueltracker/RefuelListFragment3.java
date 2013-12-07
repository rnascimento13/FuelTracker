package com.brmobsoft.fueltracker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RefuelListFragment3 extends ListFragment {
//    private static FuelTrackerDbHelper myDBHelper;
//    private static int myOldPosition;
    private static boolean isCABDestroyed;
    public static ListView myListView;
    public static FuelListAdapter myListAdapter = MyApp.fuelListAdapter;
    public static OnListSelected myListSelectedListener;//listener
    public static OnListDelete myListDeleteListener;//listener
    public interface OnListSelected { public void onListSelected(int viewId); }//listener
    public interface OnListDelete { public void onListDelete(); }//listener

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        myDBHelper = ((MyApp) getActivity().getApplication()).getDbHelper();
        getActivity().getActionBar().setSubtitle("Segure a tela para seleção multipla");
//        myOldPosition = -1;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        myListAdapter = MyApp.fuelListAdapter;
        setListAdapter(myListAdapter);
        myListView = getListView();
        myListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myListView.setOnItemLongClickListener(liListener);
        myListView.setMultiChoiceModeListener(mcListener);

    }

    @Override//listener
    public void onAttach(Activity activity) {//listener
        super.onAttach(activity);
        try {
            myListSelectedListener = (OnListSelected) activity;
            myListDeleteListener = (OnListDelete) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onViewSelected");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (FuelTrackerActivity.dualPanel) setItemSelectedOnList();
    }
    public static void setItemSelectedOnList(){ // Select Item on List only if View or Edit Fragment is loaded
//        if(isCABDestroyed) {
            myListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // make shure choice multi is not active
        isCABDestroyed = true;
//        }
        final String _FString = FuelTrackerActivity.myFM.findFragmentById(R.id.myfragment).getTag();
        if (_FString == MyApp.REFUEL_VIEW_FRAGMENT_TAG) myListView.setItemChecked(RefuelViewPagerFragment.currentItem, true);
        else if (_FString == MyApp.REFUEL_EDIT_FRAGMENT_TAG) myListView.setItemChecked(RefuelEditFragment.currentItem, true);
        else {
            myListView.setItemChecked(-1,true);
            Toast.makeText(MyApp.fuelTrackerActivity, " setItemSelecedOnList ", Toast.LENGTH_SHORT).show();
        }
    }

    private ListView.OnItemLongClickListener liListener = new ListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            myListView.dispatchSetSelected(true);
            //myListAdapter.toggleSelection(5);
//            myListView.dispatchSetActivated(false);
            myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            myListView.startActionMode(mcListener);
//            isCABDestroyed = false;
            //Toast.makeText(MyApp.fuelTrackerActivity, " OnItemLongClick ", Toast.LENGTH_SHORT).show();

            return false; // so this action does not consume the event!!!
        }
    };
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        myListAdapter.toggleSelection(5);
//        if(isCABDestroyed) {
//            myListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            //do your action command  here
//        }
//        l.setItemChecked(position, true);
//        myListSelectedListener.onListSelected(position);
    }

    private ListView.MultiChoiceModeListener mcListener = new ListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            final int checkedCount = myListView.getCheckedItemCount();
            switch (checkedCount) {
                case 0:
                    mode.setSubtitle(null);
                    break;
                case 1:
                    mode.setSubtitle("One item selected");
                    break;
                default:
                    mode.setSubtitle("" + checkedCount + " items selected");
                    break;
            }
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:
                    MyApp.fuelListAdapter = myListAdapter;
                    myListDeleteListener.onListDelete();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.delete, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isCABDestroyed = true; // mark readiness to switch back to SINGLE CHOICE after the CABis destroyed
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    };
}
