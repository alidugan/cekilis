package com.dugan.cekilis;

	import java.util.ArrayList;

	import android.content.Context;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.ArrayAdapter;
	import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<String>
{

	// declaring our ArrayList of items
	private ArrayList<String> data;

	private static LayoutInflater inflater = null;
	
	

	public CustomArrayAdapter(Context context, int textViewResourceId, ArrayList<String> data)
	{
		super(context, textViewResourceId, data);
		this.data = data;
		inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public String getItem(int position)
	{
		// TODO Auto-generated method stub
		return data.get(position);
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
		text.setText(data.get(position));
		return vi;
	}

}
