package com.dugan.cekilis;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ListViewAdapter extends ArrayAdapter<String> {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	List<String> contactList;
	private SparseBooleanArray mSelectedItemsIds;

	public ListViewAdapter(Context context, int resourceId,
			List<String> inputList) {
		super(context, resourceId, inputList);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.contactList = inputList;
		inflater = LayoutInflater.from(context);
	}

	private class ViewHolder {
		TextView name;
		TextView email;
		ImageView picture;
	}

	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.email = (TextView) view.findViewById(R.id.email);
			// Locate the ImageView in listview_item.xml
			holder.picture = (ImageView) view.findViewById(R.id.picture);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Capture position and set to the TextViews
		holder.name.setText(contactList.get(position));
		holder.email.setText(contactList.get(position));
		// Capture position and set to the ImageView
		//holder.picture.setImageResource(contactList.get(position));
		return view;
	}

	@Override
	public void remove(String object) {
		contactList.remove(object);
		notifyDataSetChanged();
	}

	public List<String> getContacts() {
		return contactList;
	}

	public void toggleSelection(int position) {
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
