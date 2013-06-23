package com.example.sqlitedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Contacts table name
	private static final String TABLE_USERS = "users";

	// Contacts Table Columns names
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_PH_NO = "phone_number";
	public static final String KEY_ADDRESS = "address";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_PH_NO + " TEXT," + KEY_ADDRESS + " TEXT" + ")";
		db.execSQL(CREATE_USERS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName()); // User Name
		values.put(KEY_PH_NO, user.getPhoneNumber()); // User Phone
		values.put(KEY_ADDRESS, user.getAddress()); // User Address

		Log.d("get get","NAME = "+user.getName());
		Log.d("get USER","PH NO = "+user.getPhoneNumber());
		Log.d("get USER","ADDRESS = "+user.getAddress());
		Log.d("values","values = "+values.toString());
		
		// Inserting Row
		db.insert(TABLE_USERS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single user
	public User getUser(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_ID, KEY_NAME,
				KEY_PH_NO, KEY_ADDRESS }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		User user = new User(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		
		db.close(); 
		// return contact
		return user;
	}

	// Getting All Contacts
	public List<User> getAllUsers() {
		List<User> contactList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setID(Integer.parseInt(cursor.getString(0)));
				user.setName(cursor.getString(1));
				user.setPhoneNumber(cursor.getString(2));
				user.setAddress(cursor.getString(3));
				// Adding contact to list
				contactList.add(user);
			} while (cursor.moveToNext());
		}
		db.close(); 
		// return contact list
		return contactList;
	}

	// Getting All Users
	public Cursor getAllUsersList() {
		// Select All Query
		//String selectQuery = "SELECT  * FROM " + TABLE_USERS;

		SQLiteDatabase db = this.getReadableDatabase();
		//Cursor cursor = db.rawQuery(selectQuery, null);
		Cursor cursor = db.query(TABLE_USERS, new String[] {KEY_ID, KEY_NAME,
				KEY_PH_NO, KEY_ADDRESS},
			    null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		db.close(); 
		// return contact list
		return cursor;
	}

	// Getting All Users
	public ArrayList<String> getAllUsersListStr() {
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USERS;
		ArrayList<String> arrayList = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		//Cursor cursor = db.rawQuery(selectQuery, null);
		Cursor cursor = db.query(TABLE_USERS, new String[] {KEY_ID, KEY_NAME,
				KEY_PH_NO, KEY_ADDRESS},
			    null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				arrayList.add(cursor.getString(1).toString());
			} while (cursor.moveToNext());
		}
		db.close(); 
		// return contact list
		return arrayList;
	}
	
	public ArrayList<HashMap<String, String>> getUserObjList(){
		
		ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
		
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				// adding each child node to HashMap key => value
				map.put(DatabaseHandler.KEY_ID, cursor.getString(0));
				map.put(DatabaseHandler.KEY_NAME, cursor.getString(1));
				map.put(DatabaseHandler.KEY_PH_NO, cursor.getString(2));
				map.put(DatabaseHandler.KEY_ADDRESS, cursor.getString(3));
				/*
				Log.d("USER","ID = "+cursor.getString(0));
				Log.d("USER","NAME = "+cursor.getString(1));
				Log.d("USER","PH NO = "+cursor.getString(2));
				Log.d("USER","ADDRESS = "+cursor.getString(3));
				Log.d("USER","COUNT = "+cursor.getCount());
				*/
				// adding HashList to ArrayList
				userList.add(map);
			} while (cursor.moveToNext());
		}
		
		db.close(); 
		return userList;
		
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	// Updating single user
	public int updateUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName());
		values.put(KEY_PH_NO, user.getPhoneNumber());
		values.put(KEY_ADDRESS, user.getAddress());

		// updating row
		return db.update(TABLE_USERS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(user.getID()) });
	}

	// Deleting single contact
	public void deleteContact(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USERS, KEY_ID + " = ?",
				new String[] { String.valueOf(user.getID()) });
		db.close();
	}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
