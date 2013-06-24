package com.example.sqlitedemo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	private ListView clv;
	public Context context;
	
	public LazyAdapter(Context context, ListView lv, Activity a,
			ArrayList<HashMap<String, String>> d) {
		this.context = context;
		clv = lv;
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public void editAddress(String userId) {

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.user_info, null);

		TextView userId = (TextView) vi.findViewById(R.id.userId); // id
		TextView userName = (TextView) vi.findViewById(R.id.userName); // user
																		// name
		TextView userPhNo = (TextView) vi.findViewById(R.id.userPhNo); // user
																		// phone
		TextView userAddress = (TextView) vi.findViewById(R.id.userAddress); // user
																				// address
		ImageView editImage = (ImageView) vi.findViewById(R.id.editBtn); // edit
																			// image

		HashMap<String, String> user = new HashMap<String, String>();
		user = data.get(position);

		// Setting all values in listview
		userId.setText(user.get(DatabaseHandler.KEY_ID));
		userName.setText(user.get(DatabaseHandler.KEY_NAME));
		userPhNo.setText(user.get(DatabaseHandler.KEY_PH_NO));
		userAddress.setText(user.get(DatabaseHandler.KEY_ADDRESS));

		editImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final int position = clv.getPositionForView((View) v
						.getParent());
				HashMap<String, String> currentUser = new HashMap<String, String>();
				currentUser = data.get(position);

				if (currentUser.get(DatabaseHandler.KEY_ID) != null) {
					Toast.makeText(
							activity,
							"Edit Image Clicked position = " + position
									+ ", User Id = "
									+ currentUser.get(DatabaseHandler.KEY_ID),
							Toast.LENGTH_SHORT).show();

					Intent i = new Intent(context, AddAddress.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
					i.putExtra("type", "edit");
					i.putExtra("userId", currentUser.get(DatabaseHandler.KEY_ID));
					context.startActivity(i);
				}

			}
		});
		return vi;
	}
}