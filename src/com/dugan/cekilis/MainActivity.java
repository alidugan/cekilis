package com.dugan.cekilis;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.util.*;

import android.view.View.OnClickListener;
import android.widget.SeekBar.*;
import android.graphics.drawable.*;

public class MainActivity extends Activity 
{
	ArrayList<String> inputs;
	
	MultiSelectionAdapter<String> adapter;
	//AppsAdapter adapter;
	ListView listView;
	EditText editText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		inputs = new ArrayList<>();
		
		//adapter = new CustomArrayAdapter(this, android.R.layout.simple_list_item_1, inputs);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inputs);
        //adapter = new CustomBaseAdapter(this, (String[])inputs);
		adapter = new MultiSelectionAdapter(this, inputs);
        //adapter = new AppsAdapter(this, (String[])inputs);
		
		
		listView = (ListView) findViewById(R.id.mainListView1);
		//listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position,
													  long id, boolean checked) {
					// Here you can do something when items are selected/de-selected,
					// such as update the title in the CAB
				}

				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					// Respond to clicks on the actions in the CAB
					switch (item.getItemId()) {
						case R.id.action_delete:
							//deleteSelectedItems();
							mode.finish(); // Action picked, so close the CAB
							return true;
						default:
							return false;
					}
				}

				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					// Inflate the menu for the CAB
					
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.list_top_cab, menu);
					return true;
				}

				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// Here you can make any necessary updates to the activity when
					// the CAB is removed. By default, selected items are deselected/unchecked.
				}

				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// Here you can perform updates to the CAB due to
					// an invalidate() request
					return false;
				}
			});
		listView.setAdapter(adapter);
	

		listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> l, View v, int position, long id)
				{
					//DeleteDialogFragment f = new DeleteDialogFragment();				
					//f.show(getFragmentManager(), "mydialog");
					
					String s = (String) l.getItemAtPosition(position);
					Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
					
				}
			});

		editText = (EditText) findViewById(R.id.mainEditText1);

		Button ekleButton = (Button) findViewById(R.id.mainButton1);

		ekleButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					//String[] addr = editText.getText().toString().split("@");

					if ("".equals(editText.getText().toString()))
					//if (!editText.isDirty())
					{
						return;
					}

					//ListAdapter ad = listView.getAdapter();
					
					String input = editText.getText().toString();
					inputs.add(input);
					//adapter.addItem(input);
					editText.setText("");
					adapter.notifyDataSetChanged();
					//ListAdapter adap = new ArrayAdapter<String>(p1.getContext(), android.R.layout.simple_list_item_1, inputs);
					//listView.setAdapter(ad);

				}
			});
			
		
		Button cekButton = (Button) findViewById(R.id.mainButton2);
		cekButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					//ArrayList<String> peers = Raffle.setPairsAli(inputs);
					ArrayList<String> peers = Raffle.setPairsNazim(inputs);
					
					for(int i=0;i<inputs.size();++i){
						Log.e("inputs", inputs.get(i));
						
					}
					for(int i=0;i<peers.size();++i){
						Log.e("peers", peers.get(i));
						//new AsyncEmailTask().execute(inputs.get(i),peers.get(i));
					}

				}
			});
		
		
		Button silButton = (Button) findViewById(R.id.deleteButton);
		silButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View p1)
				{

					if (adapter != null)
					{
						ArrayList<String> inputList = adapter.getCheckedItems();

						//Log.d(MainActivity.class.getSimpleName(), "deleted : " + inputList.toString());
						Toast.makeText(getApplicationContext(), "deleted : " + inputList.toString(), Toast.LENGTH_LONG).show();
						
						adapter.deleteCheckedItems();
						adapter.notifyDataSetChanged();
					}
				}
			});			
			
	}
		


    private class AsyncEmailTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {   
				GmailSender sender = new GmailSender("cekilisapp@gmail.com", "Duga1002");
				sender.sendMail(params[0],   
								params[1],   
								"cekilisapp@gmail.com",   
								"alidugan@gmail.com");   
			} catch (Exception e) {   
				Log.e("SendMail", e.getMessage(), e);   
			} 
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }	
	
	
	class DeleteDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Sil");
			builder.setMessage("Katılımcı silinsin mi?");
			builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						// TODO: Implement this method
						getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
					}
				});

			builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						// TODO: Implement this method
					}
				});

			return builder.create();
		}
		
		
	}
			
	/*public class AppsAdapter extends BaseAdapter {
		public AppsAdapter() {
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			CheckableLayout l;
			ImageView i;

			if (convertView == null) {
				i = new ImageView(MainActivity.this);
				i.setScaleType(ImageView.ScaleType.FIT_CENTER);
				i.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
				l = new CheckableLayout(MainActivity.this);
				l.setLayoutParams(new GridView.LayoutParams(
									  GridView.LayoutParams.WRAP_CONTENT,
									  GridView.LayoutParams.WRAP_CONTENT));
				l.addView(i);
			} else {
				l = (CheckableLayout) convertView;
				i = (ImageView) l.getChildAt(0);
			}

			ResolveInfo info = mApps.get(position);
			i.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));

			return l;
		}

		public final int getCount() {
			return inputs.size();
		}

		public final Object getItem(int position) {
			return inputs.get(position);
		}

		public final long getItemId(int position) {
			return position;
		}
	}*/

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

	public class MultiChoiceModeListener implements
	ListView.MultiChoiceModeListener {
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.setTitle("Select Items");
			mode.setSubtitle("One item selected");
			return true;
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}

		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			return true;
		}

		public void onDestroyActionMode(ActionMode mode) {
		}

		public void onItemCheckedStateChanged(ActionMode mode, int position,
											  long id, boolean checked) {
			int selectCount = listView.getCheckedItemCount();
			switch (selectCount) {
				case 1:
					mode.setSubtitle("One item selected");
					break;
				default:
					mode.setSubtitle("" + selectCount + " items selected");
					break;
			}
		}

	}		

    
}
