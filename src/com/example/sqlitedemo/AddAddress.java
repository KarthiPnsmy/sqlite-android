package com.example.sqlitedemo;

import com.example.sqlitedemo.DatabaseHandler;
import com.example.sqlitedemo.User;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAddress extends Activity{

	String formType;
	DatabaseHandler db; 
	Integer userId;
	
	public AddAddress() {
		db = new DatabaseHandler(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_address);
		
		Button saveBtn = (Button) findViewById(R.id.saveBtn);
		
		final EditText nameField = (EditText) findViewById(R.id.userName);
		final EditText addressField = (EditText) findViewById(R.id.addressText);
		final EditText phoneField = (EditText) findViewById(R.id.phoneNo);
		
		formType = getIntent().getStringExtra("type");
		if(formType.equalsIgnoreCase("edit")){
			userId = Integer.parseInt(getIntent().getStringExtra("userId"));
			saveBtn.setText("Update");
			Log.d("userId", "userId = "+userId.toString());
			
			User currentUser = db.getUser(userId);
			String currentUserName = currentUser.getName();
			String currentUsePhoneNo = currentUser.getPhoneNumber();
			String currentUserAddress = currentUser.getAddress();
			
			Log.d("Info ", "currentUserName = "+currentUserName+", currentUsePhoneNo = "+currentUsePhoneNo+", currentUserAddress = "+currentUserAddress);
			nameField.setText(currentUserName);
			phoneField.setText(currentUsePhoneNo);
			addressField.setText(currentUserAddress);
		}
		
		
		saveBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 String userNameText = nameField.getText().toString();
				 String userAddressText = addressField.getText().toString();
				 String userPhoneText = phoneField.getText().toString();

				 if (formType.equalsIgnoreCase("edit")){
					// Updating User
					db.updateUser(new User(userId, userNameText, userPhoneText, userAddressText));
					Toast.makeText(getApplicationContext(), "Update button clicked \nName:"+userNameText.toString()+"\nPhone No:"+userPhoneText+"\nAddressText:"+userAddressText, Toast.LENGTH_LONG).show(); 
				 }else{
			        // Inserting User
			        Log.d("Insert: ", "Inserting ..");
			        db.addUser(new User(userNameText, userPhoneText, userAddressText));
					Toast.makeText(getApplicationContext(), "Save button clicked \nName:"+userNameText.toString()+"\nPhone No:"+userPhoneText+"\nAddressText:"+userAddressText, Toast.LENGTH_LONG).show(); 
				 }
				finish();
			}
		});
	}
	
}
