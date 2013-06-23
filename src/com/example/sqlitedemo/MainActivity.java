package com.example.sqlitedemo;

import com.example.sqlitedemo.DatabaseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final DatabaseHandler db = new DatabaseHandler(this);
		Button addBtn = (Button) findViewById(R.id.addBtn);

		addBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Add button clicked",
						Toast.LENGTH_LONG).show();

				Intent i = new Intent(getApplicationContext(), AddAddress.class);
				i.putExtra("type", "new");
				startActivity(i);
			}
		});

		ListView lv = (ListView) findViewById(R.id.user_list);
		
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String userId = ((TextView) view.findViewById(R.id.userId)).getText().toString();
				//Log.i("userId", userId);
				//Log.i("userId", "position = "+position);
				//Log.i("id", "id = "+id);
				
				Intent i = new Intent(getApplicationContext(), AddAddress.class);
				i.putExtra("type", "edit");
				i.putExtra("userId", userId);
				startActivity(i);
				
				Toast.makeText(getApplicationContext(), "userId = "+userId, Toast.LENGTH_SHORT).show();
			}
		});
        /*
		Log.d("getUserObjList","db.getUserObjList() = "+db.getUserObjList().toString());
		ListAdapter adapter = new SimpleAdapter(
				this, db.getUserObjList(),
				R.layout.user_info, new String[] { DatabaseHandler.KEY_ID, DatabaseHandler.KEY_NAME, DatabaseHandler.KEY_PH_NO, DatabaseHandler.KEY_ADDRESS }, new int[] {R.id.userId, R.id.userName, R.id.userPhNo, R.id.userAddress });

		lv.setAdapter(adapter);
		db.close();
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void refreshList(){
		DatabaseHandler db = new DatabaseHandler(this);
		ListView lv = (ListView) findViewById(R.id.user_list);
		ListAdapter adapter = new SimpleAdapter(
				this, db.getUserObjList(),
				R.layout.user_info, new String[] { DatabaseHandler.KEY_ID, DatabaseHandler.KEY_NAME, DatabaseHandler.KEY_PH_NO, DatabaseHandler.KEY_ADDRESS }, new int[] {R.id.userId, R.id.userName, R.id.userPhNo, R.id.userAddress });

		lv.setAdapter(adapter);
		db.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("onResume","onResume called");
		refreshList();
	}
}
