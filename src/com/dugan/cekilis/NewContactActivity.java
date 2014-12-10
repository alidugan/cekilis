package com.dugan.cekilis;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.RadioGroup.*;

import android.view.View.OnClickListener;
public class NewContactActivity extends Activity {
	EditText editTextName;
	private String name = "";
	//intent for use when returning to main activity
	Intent newContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newcontact);
		editTextName = (EditText) findViewById(R.id.name);
		
		//get our OK Button
		Button ok = (Button) findViewById(R.id.buttonOk);
		//create new selectedColor Intent
		newContact = new Intent();
		
		//set action listener for Ok Button
		ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if ("".equals(editTextName.getText().toString())) {
						return;
					}

					name = editTextName.getText().toString();
					newContact.putExtra("name", name);
					//set result with our selectedColor intent and RESULT_OK request code
					setResult(RESULT_OK, newContact);
					//then finish the activity
					finish();
				}
			});
			
		Button cancel = (Button) findViewById(R.id.buttonCancel);
		cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					finish();
				}
			});
	}
}
