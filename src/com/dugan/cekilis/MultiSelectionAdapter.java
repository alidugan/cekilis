package com.dugan.cekilis;

import android.content.*;
import android.content.pm.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.CompoundButton.*;
import java.util.*;


public class MultiSelectionAdapter<T> extends BaseAdapter
{
	Context mContext;
	LayoutInflater mInflater;

	ArrayList<T> mList;
	SparseBooleanArray mSparseBooleanArray;


	public MultiSelectionAdapter(Context context, ArrayList<T> list)
	{
		// TODO Auto-generated constructor stub
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mSparseBooleanArray = new SparseBooleanArray();
		mList = new ArrayList<T>();
		this.mList = list;

	}

	public void addItem(T item) {
		
		mList.add(item);
	}

	public ArrayList<T> getCheckedItems()
	{
		ArrayList<T> mTempArry = new ArrayList<T>();

		for (int i=0;i < mList.size();i++)
		{
			if (mSparseBooleanArray.get(i))
			{
				mTempArry.add(mList.get(i));
			}
		}

		return mTempArry;
	}

	public void deleteCheckedItems()
	{
		ArrayList<T> mTempArry = new ArrayList<T>();

		for (int i=0;i < mList.size();i++)
		{


			if (mSparseBooleanArray.get(i))
			{
				mList.remove(i);


				//mTempArry.add(mList.get(i));


			}


		}





		//return mTempArry;


	}


	@Override


	public int getCount()
	{
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		CheckableLayout l;
		TextView i;

		if (convertView == null) {
			i = new TextView(mContext);
			//i.setScaleType(ImageView.ScaleType.FIT_CENTER);
			i.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
			l = new CheckableLayout(mContext);
			l.setLayoutParams(new ListView.LayoutParams(
								  ListView.LayoutParams.WRAP_CONTENT,
								  ListView.LayoutParams.WRAP_CONTENT));
			l.addView(i);
		} else {
			l = (CheckableLayout) convertView;
			i = (TextView) l.getChildAt(0);
		}
		
		
		

		//T info = mList.get(position);
		i.setText(mList.get(position).toString());
		//i.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));

		return l;
		
		
		// TODO Auto-generated method stub
		/*if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.row2, null);
		}
		
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
		tvTitle.setText(mList.get(position).toString());


		CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.chkEnable);
		mCheckBox.setTag(position);
		mCheckBox.setChecked(mSparseBooleanArray.get(position));
		mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);

		return convertView;*/
	}

	OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
			// TODO Auto-generated method stub
			mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
		}
	};

	
		public class CheckableLayout extends FrameLayout implements Checkable {
		private boolean mChecked;

		public CheckableLayout(Context context) {
			super(context);
		}

		@SuppressWarnings("deprecation")
		public void setChecked(boolean checked) {
			mChecked = checked;
			setBackgroundDrawable(checked ? getResources().getDrawable(
									  R.drawable.ic_launcher) : null);
		}

		public boolean isChecked() {
			return mChecked;
		}

		public void toggle() {
			setChecked(!mChecked);
		}

	}
	
}
