package com.brmobsoft.fueltracker;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class  FuelListAdapter extends BaseAdapter {
	private static final String TAG = "FuelTracker.log";
	private Context context;
	private LayoutInflater mInflater = null;
	private List<FuelItem> mItems = new ArrayList<FuelItem>();
    private SparseBooleanArray mSelectedItemsIds;
    private boolean isOnCAB = false;

	public FuelListAdapter(Context context) {
		Log.d(TAG, "Constructor");
		this.context = context;
		mInflater = LayoutInflater.from(context);
        MyApp.fuelListAdapter = this;
        mSelectedItemsIds = new SparseBooleanArray();
	}

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    public void addItem(FuelItem it) {
		mItems.add(it);
	}

//	public void setListItems(List<FuelItem> lit) {
//		mItems = lit;
//	}

	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

    public FuelItem getFuelItem(int position) {
        return mItems.get(position);
    }

    public void setOnCAB(boolean onCAB) {
        isOnCAB = onCAB;
    }
    public boolean getOnCAB() {
        return isOnCAB;
    }


    public boolean isIDValid(long rowID) {
        if (rowID == MyApp.NULL_FRAGMENT_LONG) return false;
        if ( (rowID == MyApp.REFUEL_LIST_FRAGMENT_LONG) && FuelTrackerActivity.dualPanel ) return false;


        if (rowID < 0) return true;
        for (int x = 0; x < mItems.size(); x++) { // look for deleted entry on backStack
            if (mItems.get(x).getId() == rowID) {
                return true;
            }
        }
        return false;
    }

    public int getItemPositionByID(long rowID) {
        for (int i = 0; i < mItems.size(); i++) { // look for deleted entry on backStack
            if (mItems.get(i).getId() == rowID) {
                return i;
            }
        }
        return -1;
    }
    public long getFuelItemID(int position) {
        return mItems.get(position).getId();
    }

    public long getItemId(int position) {
		return position;
	}
    public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder;
		if (convertView != null)
			vHolder = (ViewHolder) convertView.getTag();
		else {
            convertView = mInflater.inflate(R.layout.list_item, null);
			vHolder = new ViewHolder();
            vHolder.textView = ((TextView) convertView.findViewById(R.id.listText));
            vHolder.textView2 = ((TextView) convertView.findViewById(R.id.listText2));
            vHolder.imageView = ((ImageView) convertView.findViewById(R.id.listTypeImage));
            vHolder.relativeLayout = ((RelativeLayout) convertView.findViewById(R.id.relativeLayout));
//            vHolder.linearLayout = ((LinearLayout) convertView.findViewById(R.id.linearLayout));
			convertView.setTag(vHolder);
		}
        FuelItem b = mItems.get(position);
		vHolder.textView.setId(position);
        String mDate = b.getDateString();
        if (b.getType()==1) {
            vHolder.imageView.setImageResource(R.drawable.ic_g_blue);
            vHolder.textView.setText(b.getQuantity()+" Litros - "+b.getPay()+" Reais position:"+position);
            vHolder.textView2.setText(mDate);
        }
        if (b.getType()==0) {
            vHolder.imageView.setImageResource(R.drawable.ic_a_green);
            vHolder.textView.setText(b.getQuantity()+" Litros - "+b.getPay()+" Reais position:"+position);
            vHolder.textView2.setText(mDate);
        }

//        if (position == MyApp.highlightedItemPosition) {
            // if this item is checked - set checked state
//            convertView.getBackground().setState( new int[] { android.R.attr.state_checked });
//        } else {
            // if this item is unchecked - set unchecked state (notice the minus)
//            convertView.getBackground().setState( new int[] { -android.R.attr.state_checked });
//        }


//        if (mSelectedItemsIds.get(position)) convertView.setBackgroundColor(MyApp.fuelTrackerActivity.getResources().getColor(R.color.holo_red_light));

        if (isOnCAB && RefuelListFragment.myListView.isItemChecked(position)) convertView.setBackgroundColor(MyApp.fuelTrackerActivity.getResources().getColor(R.color.holo_red_light));
        else if (!isOnCAB && (position == MyApp.highlightedItemPosition) ) convertView.setBackgroundColor(MyApp.fuelTrackerActivity.getResources().getColor(R.color.holo_green_light));
        else convertView.setBackgroundColor(MyApp.fuelTrackerActivity.getResources().getColor(R.color.white_opaque)); // color change

        return convertView;
	}

    public void setItemBackground(int position, ListView l, int resourceID) {

        //l.setItemChecked(position, true);
//        l.setSelection(position);
//        l.setSelected(true);

        //l.setItemChecked(position,true);
        //l.setItemChecked(position,true);
        //l.setSelection(position);
        //l.getChildAt(position).setSelected(true);
        //l.getChildAt(position).setActivated(true);
        View wantedView = l.getChildAt(position);
        //if (wantedView != null) wantedView.setActivated(true);
//        if (wantedView != null) wantedView.setBackgroundResource(resourceID);
//        if (position == MyApp.highlightedItemPosition) {
            // if this item is checked - set checked state
//            wantedView.getBackground().setState( new int[] { android.R.attr.state_checked });
//        } else {
            // if this item is unchecked - set unchecked state (notice the minus)
//            wantedView.getBackground().setState( new int[] { -android.R.attr.state_checked });
//        }

    }
	public static class ViewHolder {
		TextView textView;
		TextView textView2;
        ImageView imageView;
        //LinearLayout linearLayout;
        RelativeLayout relativeLayout;
    }
    public void toggleSelection(int position) {
//todo nullpointer on list longpress
        selectView(position, !mSelectedItemsIds.get(position));
    }
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}