package com.dugan.cekilis;

import android.*;
import android.content.*;
import android.view.*;
import android.widget.*;


public class CustomBaseAdapter extends BaseAdapter
{

	Context context;
	String[] data;
	private static LayoutInflater inflater = null;

	public CustomBaseAdapter(Context context, String[] data)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return data[position];
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		View vi = convertView;
		if (vi == null)
			vi = inflater.inflate(R.layout.row, null);
		TextView text = (TextView) vi.findViewById(R.id.rowTextView1);
		text.setText(data[position]);
		return vi;
	}
}
