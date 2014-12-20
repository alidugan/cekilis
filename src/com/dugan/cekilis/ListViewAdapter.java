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
import com.sun.mail.imap.protocol.*;

public class ListViewAdapter extends ArrayAdapter<Contact> {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	List<Contact> mList;
	private SparseBooleanArray mSelectedItemsIds;

	public ListViewAdapter(Context context, int resourceId,
			List<Contact> inputList) {
		super(context, resourceId, inputList);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.mList = inputList;
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
		Contact item = getItem(position);
		// Capture position and set to the TextViews
		holder.name.setText(item.getName());
		holder.email.setText(item.getEmail());
		// Capture position and set to the ImageView
		//holder.picture.setImageResource(item.getPicture());
		return view;
	}

	@Override
	public void remove(Contact object) {
		mList.remove(object);
		notifyDataSetChanged();
	}

	public List<Contact> getContacts() {
		return mList;
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
