package com.dugan.cekilis;

import android.app.*;
import android.content.*;
import android.database.*;
import android.os.*;
import android.provider.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.AbsListView.*;
import android.widget.AdapterView.*;
import java.util.*;

import android.widget.AdapterView.OnItemClickListener;
import com.sun.mail.imap.protocol.*;

public class PhoneContactsActivity extends Activity 
{
	ArrayList<Contact> contacts;
	Intent intent;

	ListView listView;
	ListViewAdapter adapter;
	

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_main);
		
		intent = new Intent();

		contacts = getNameEmailDetails();

		listView = (ListView) findViewById(R.id.listview);

		adapter = new ListViewAdapter(this, R.layout.listview_item,
									  contacts);

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
							ArrayList<String> names = new ArrayList<String>();
							ArrayList<String> emails = new ArrayList<String>();
							for (int i = (selected.size() - 1); i >= 0; i--) {
								if (selected.valueAt(i)) {
									Contact selecteditem = (Contact)adapter
										.getItem(selected.keyAt(i));
										
									names.add(selecteditem.getName());
									emails.add(selecteditem.getEmail());
									
								}
							}
							
							
							intent.putStringArrayListExtra("names", names);
							intent.putStringArrayListExtra("emails", emails);

							setResult(RESULT_OK, intent);
							//then finish the activity
							finish();
							
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
					MenuItem item = menu.findItem(R.id.delete);
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

					String s = (String) l.getItemAtPosition(position);
					Toast.makeText (PhoneContactsActivity.this, s, Toast.LENGTH_SHORT).show();

				}
			});
			
		EditText inputSearch = (EditText) findViewById(R.id.PhoneContactSearch);
		inputSearch.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
					// When user changed the Text
					PhoneContactsActivity.this.adapter.getFilter().filter(cs);   
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
											  int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub                          
				}
			});

	}
	
	
	public ArrayList<Contact> getNameEmailDetails() {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		HashSet<String> emlRecsHS = new HashSet<String>();
		Context context = getApplicationContext();
		ContentResolver cr = context.getContentResolver();
		String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID, 
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.CommonDataKinds.Email.DATA, 
            ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
		String order = "CASE WHEN " 
            + ContactsContract.Contacts.DISPLAY_NAME 
            + " NOT LIKE '%@%' THEN 1 ELSE 2 END, " 
            + ContactsContract.Contacts.DISPLAY_NAME 
            + ", " 
            + ContactsContract.CommonDataKinds.Email.DATA
            + " COLLATE NOCASE";
		String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
		Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
		if (cur.moveToFirst()) {
			do {
				// names comes in hand sometimes
				String name = cur.getString(1);
				String email = cur.getString(3);

				// keep unique only
				if (emlRecsHS.add(email.toLowerCase())) {
					Contact c = new Contact();
					c.setName(name);
					c.setEmail(email);
					contacts.add(c);
				}
			} while (cur.moveToNext());
		}

		cur.close();
		return contacts;
	}
	
	
	public ArrayList<Contact> getNameEmailDetailsSlow() {
		ArrayList<Contact> contacts = new ArrayList<Contact>();

		ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
							  null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
				String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (Integer.parseInt(cur.getString(
										 cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
						new String[]{id}, null);
					while (pCur.moveToNext()) {
						//String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						String email = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
						//Log.e("email:", email);
						if (email != null) {
							Contact contact = new Contact();
							contact.setName(name);
							contact.setEmail(email);
							contacts.add(contact);
						}
					}
                    pCur.close();
                }
            }
        }
		return contacts;
	}

}
