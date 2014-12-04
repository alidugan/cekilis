package com.dugan.cekilis;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AbsListView.*;
import android.widget.AdapterView.*;
import java.util.*;

import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity 
{
	ArrayList<String> inputs;
	
	ListView list;
	ListViewAdapter listviewadapter;
	EditText editText;
	Button cekButton;
	Button ekleButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		inputs = new ArrayList<>();
		
		// Locate the ListView in listview_main.xml
		list = (ListView) findViewById(R.id.mainListView1);
		
		listviewadapter = new ListViewAdapter(this, R.layout.listview_item,
											  inputs);

		// Binds the Adapter to the ListView
		list.setAdapter(listviewadapter);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		// Capture ListView item click
		list.setMultiChoiceModeListener(new MultiChoiceModeListener() {

				@Override
				public void onItemCheckedStateChanged(ActionMode mode,
													  int position, long id, boolean checked) {
					// Capture total checked items
					final int checkedCount = list.getCheckedItemCount();
					// Set the CAB title according to total checked items
					mode.setTitle(checkedCount + " Selected");
					// Calls toggleSelection method from ListViewAdapter Class
					listviewadapter.toggleSelection(position);
				}

				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					switch (item.getItemId()) {
						case R.id.delete:
							// Calls getSelectedIds method from ListViewAdapter Class
							SparseBooleanArray selected = listviewadapter
								.getSelectedIds();
							// Captures all selected ids with a loop
							for (int i = (selected.size() - 1); i >= 0; i--) {
								if (selected.valueAt(i)) {
									String selecteditem = listviewadapter
										.getItem(selected.keyAt(i));
									// Remove selected items following the ids
									listviewadapter.remove(selecteditem);
								}
							}
							// Close CAB
							mode.finish();
							return true;
						default:
							return false;
					}
				}

				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					mode.getMenuInflater().inflate(R.menu.activity_main, menu);
					return true;
				}

				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// TODO Auto-generated method stub
					listviewadapter.removeSelection();
				}

				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					return false;
				}
			});
	

		list.setOnItemClickListener(new OnItemClickListener() {

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

		ekleButton = (Button) findViewById(R.id.mainButton1);
		ekleButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					//String[] addr = editText.getText().toString().split("@");

					if ("".equals(editText.getText().toString())) {
						return;
					}
					
					String input = editText.getText().toString();
					inputs.add(input);
					editText.setText("");
					listviewadapter.notifyDataSetChanged();

				}
			});
			
		
		cekButton = (Button) findViewById(R.id.mainButton2);
		cekButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					for(int i=0;i<inputs.size();++i){
						Log.e("inputs", inputs.get(i));

					}
					
					//ArrayList<String> peers = Raffle.setPairsAli(inputs);
					ArrayList<String> peers = Raffle.setPairsNazim(inputs);
					
					
					for(int i=0;i<peers.size();++i){
						Log.e("peers", peers.get(i));
						//new AsyncEmailTask().execute(inputs.get(i),peers.get(i));
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
			
	

}
