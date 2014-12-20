package com.dugan.cekilis;

import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
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
	private static final  int NEWCONTACT_REQUEST=0;
	private static final  int PHONECONTACT_REQUEST=1;
	ArrayList<Contact> inputs;
	
	ListView listView;
	ListViewAdapter adapter;
	EditText editText;
	Button cekButton;
	Button ekleButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		inputs = new ArrayList<Contact>();
		
		listView = (ListView) findViewById(R.id.mainListView1);
		
		adapter = new ListViewAdapter(this, R.layout.listview_item,
											  inputs);

		// Binds the Adapter to the ListView
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		// Capture ListView item click
		listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

				@Override
				public void onItemCheckedStateChanged(ActionMode mode,
													  int position, long id, boolean checked) {
					// Capture total checked items
					final int checkedCount = listView.getCheckedItemCount();
					// Set the CAB title according to total checked items
					mode.setTitle(checkedCount + " Selected");
					// Calls toggleSelection method from ListViewAdapter Class
					adapter.toggleSelection(position);
				}

				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					switch (item.getItemId()) {
						case R.id.delete:
							// Calls getSelectedIds method from ListViewAdapter Class
							SparseBooleanArray selected = adapter
								.getSelectedIds();
							// Captures all selected ids with a loop
							for (int i = (selected.size() - 1); i >= 0; i--) {
								if (selected.valueAt(i)) {
									Contact selecteditem = (Contact)adapter
										.getItem(selected.keyAt(i));
									// Remove selected items following the ids
									adapter.remove(selecteditem);
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
					MenuItem item = menu.findItem(R.id.add);
					item.setVisible(false);
					return true;
				}

				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// TODO Auto-generated method stub
					adapter.removeSelection();
				}

				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					return false;
				}
			});
	

		listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> l, View v, int position, long id)
				{
					//DeleteDialogFragment f = new DeleteDialogFragment();				
					//f.show(getFragmentManager(), "mydialog");
					
					//String s = (String) l.getItemAtPosition(position);
					//Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
					
				}
			});

		
		ekleButton = (Button) findViewById(R.id.mainButton1);
		ekleButton.setOnClickListener(new OnClickListener() {
/*
				@Override
				public void onClick(View p1)
				{
					AddDialogFragment f = new AddDialogFragment();				
					f.show(getFragmentManager(), "adddialog");
					
					
					//String[] addr = editText.getText().toString().split("@");
					if ("".equals(editText.getText().toString())) {
						return;
					}
					
					String input = editText.getText().toString();
					inputs.add(input);
					editText.setText("");
					listviewadapter.notifyDataSetChanged();

				}*/
				
				@Override
				public void onClick(View v) {
					Intent request =new Intent(MainActivity.this, NewContactActivity.class);
					
					startActivityForResult(request, NEWCONTACT_REQUEST);    
				}
		
			});
			
		Button rehber = (Button) findViewById(R.id.mainButtonRehber);
		rehber.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent request =new Intent(MainActivity.this, PhoneContactsActivity.class);

					startActivityForResult(request, PHONECONTACT_REQUEST);    
				}

			});
			
		
		cekButton = (Button) findViewById(R.id.mainButton2);
		cekButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					if (inputs.size() < 3) {
						Toast.makeText(getApplicationContext(), R.string.warning1, Toast.LENGTH_SHORT).show();
						return;
					}
					
					for(int i=0;i<inputs.size();++i){
						Log.e("inputs", inputs.get(i).getName());

					}
					
					Raffle<Contact> raffle = new Raffle<>();
					//ArrayList<String> peers = Raffle.setPairsAli(inputs);
					ArrayList<Contact> peers = raffle.setPairsNazim(inputs);
					
					
					for(int i=0;i<peers.size();++i){
						Log.e("peers", peers.get(i).getName());
						//new AsyncEmailTask().execute(inputs.get(i).getName(), inputs.get(i).getEmail(),peers.get(i).getName(),peers.get(i).getEmail());
					}

				}
			});
					
			
	}
		
    //this is the method that call when Activity result comes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//check which activity gives data
		switch(requestCode){
			case NEWCONTACT_REQUEST:
				//check whether result comes with RESULT_OK (That mean no problem in result)
				if(resultCode == RESULT_OK){
					Contact newContact = new Contact();
					
				    newContact.setName(data.getExtras().getString("name"));
					newContact.setEmail(data.getExtras().getString("email"));
					
					for (Contact contact: inputs) {
						if (contact.getEmail().equals(newContact.getEmail())) {
							Toast.makeText(getApplicationContext(), R.string.warning3, Toast.LENGTH_SHORT).show();
							return;
						}
					}
					
					inputs.add(newContact);
					adapter.notifyDataSetChanged();
					
				}
				break;
			case PHONECONTACT_REQUEST:
				
				if(resultCode == RESULT_OK){
					ArrayList<String> names = data.getExtras().getStringArrayList("names");
					ArrayList<String> emails = data.getExtras().getStringArrayList("emails");
					
					boolean duplicate;
					for (int i=0; i < names.size(); ++i) {
						Contact newContact = new Contact();
				    	newContact.setName(names.get(i));
						newContact.setEmail(emails.get(i));
						
						duplicate = false;
						for (Contact contact: inputs) {
							if (contact.getEmail().equals(newContact.getEmail())) {
								Toast.makeText(getApplicationContext(), R.string.warning3, Toast.LENGTH_SHORT).show();
								duplicate = true;
								
							}
						}
						
						if (!duplicate) {
							inputs.add(newContact);
						}
					}
					adapter.notifyDataSetChanged();

				}
				break;
		}
    }

    private class AsyncEmailTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {   
				GmailSender sender = new GmailSender("cekilisapp@gmail.com", "Duga1002");
				String subject = "Çekiliş sonucu";
				String body = "Hediye vereceğiniz kişi : " + params[2];
				String rec_addr = params[1];
				sender.sendMail(subject,   
								body,   
								"cekilisapp@gmail.com",   
								rec_addr);   
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
			
	class AddDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Yeni kişi");
			TextView namelabel = new TextView(getActivity());
			namelabel.setText("isim");
			TextView emaillabel = new TextView(getActivity());
			emaillabel.setText("email");
			EditText name = new EditText(getActivity());
			EditText email = new EditText(getActivity());
			builder.setView(namelabel);
			builder.setView(name);
			builder.setView(emaillabel);
			//builder.setView(email);
			
			builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						// TODO: Implement this method
						//getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
						//((MainActivity)getActivity()).doPositiveClick();
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
