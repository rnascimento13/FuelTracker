package com.brmobsoft.fueltracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RefuelListFragment extends ListFragment {
//    private static int myOldPosition;
    public static ListView myListView;
    public static FuelListAdapter myListAdapter = MyApp.fuelListAdapter;
    public static OnListSelected myListSelectedListener;//listener
    public static OnListDelete myListDeleteListener;//listener
    public interface OnListSelected { public void onListSelected(int viewId); }//listener
    public interface OnListDelete { public void onListDelete(); }//listener

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().setSubtitle("Segure a tela para seleção multipla");
//        myOldPosition = -1;
    }
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        myListAdapter = MyApp.fuelListAdapter;
        setListAdapter(myListAdapter);
        myListView = getListView();
//        myListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        myListView.setMultiChoiceModeListener(new ModeCallback());
        myListView.setFastScrollEnabled(true);
        myListView.setScrollingCacheEnabled(false);
//        myListView.setSelector(R.drawable.selector_list_multimode);
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

    private class ModeCallback implements ListView.MultiChoiceModeListener {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            final int checkedCount = myListView.getCheckedItemCount();
            mode.setTitle(checkedCount + " Selected");
            myListAdapter.toggleSelection(position); // to mark on a sparseBoolean
            myListAdapter.notifyDataSetChanged(); // to refresh the adapter
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
            mode.getMenuInflater().inflate(R.menu.delete, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // TODO Auto-generated method stub
            myListAdapter.removeSelection();
            myListAdapter.setOnCAB(false);
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub
            myListAdapter.setOnCAB(true);
            return false;
        }
    }
    public void onListItemClick (ListView l, View v, int position, long id){
        if (FuelTrackerActivity.dualPanel) { // to avoid bug that show the keyboard on dual panel
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        myListSelectedListener.onListSelected(position);
        myListAdapter.notifyDataSetChanged();
    }
}