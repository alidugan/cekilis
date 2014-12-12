package com.dugan.cekilis;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.view.inputmethod.*;
import android.widget.*;
public class NewContactActivity extends Activity {
	EditText nameEditText;
	EditText emailEditText;
	private String name = "";
	private String email = "";
	//intent for use when returning to main activity
	Intent newContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newcontact);
		nameEditText = (EditText) findViewById(R.id.name);
		emailEditText = (EditText) findViewById(R.id.email);
		
		// Request focus and show soft keyboard automatically
        nameEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(
			Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(nameEditText, 0);
		
		//get our OK Button
		Button ok = (Button) findViewById(R.id.buttonOk);
		
		newContact = new Intent();
		
		//set action listener for Ok Button
		ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if ("".equals(emailEditText.getText().toString())) {
						Toast.makeText(getApplicationContext(), R.string.warning2, Toast.LENGTH_SHORT).show();
						return;
					}

					name = nameEditText.getText().toString();
					newContact.putExtra("name", name);
					email = emailEditText.getText().toString();
					newContact.putExtra("email", email);
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
