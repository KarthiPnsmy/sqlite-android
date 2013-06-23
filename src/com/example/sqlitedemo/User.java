package com.example.sqlitedemo;

public class User {
	//private variables
	int _id;
	String _name;
	String _phone_number;
	String _address;
	
	// Empty constructor
	public User(){
		
	}
	// constructor
	public User(int id, String name, String _phone_number, String _address){
		this._id = id;
		this._name = name;
		this._phone_number = _phone_number;
		this._address = _address;
	}
	
	// constructor
	public User(String name, String _phone_number, String _address){
		this._name = name;
		this._phone_number = _phone_number;
		this._address = _address;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting phone number
	public String getPhoneNumber(){
		return this._phone_number;
	}
	
	// setting phone number
	public void setPhoneNumber(String phone_number){
		this._phone_number = phone_number;
	}
	
	// getting address
	public String getAddress(){
		return this._address;
	}
	
	// setting address
	public void setAddress(String address){
		this._address = address;
	}
}
