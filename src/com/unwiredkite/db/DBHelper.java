package com.unwiredkite.db;

import java.util.ArrayList;
import java.util.HashMap;

import com.unwiredkite.entity.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	public final static String DB_NAME = "contact";
	public final static int VERSION = 1;
	
	private static DBHelper instance = null;
	
	private SQLiteDatabase db;
	private static Context mContext;
	
	public static DBHelper getInstance(Context context) {
		if(instance == null) {
			instance = new DBHelper(context);
			mContext = context;
		}
		
		return instance;
	}
	
	private void openDatabase() {
		if(db == null) {
			db = this.getWritableDatabase();
		}
	}

	private DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		StringBuffer tableCreate = new StringBuffer();
		tableCreate.append("create table user (_id integer primary key autoincrement,")
					.append("name text,")
					.append("mobilephone text)");
		db.execSQL(tableCreate.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "drop table if exists user";
		db.execSQL(sql);
		onCreate(db);
	}
	
	public long save(User user) {
		openDatabase();
		ContentValues value = new ContentValues();
		value.put("name", user.name);
		value.put("mobilephone", user.mobilePhone);
		//value.put("imageid", user.imageId);

		return db.insert("user", null, value);
	}
	
	public void delete(User user) {
		openDatabase();
		db.delete("user", "name =?", new String[] {user.name.toString() });
	}
	
	public long modify(User user) {
		ContentValues value = new ContentValues();
		value.put("name", user.name);
		value.put("mobilephone", user.mobilePhone);
		return db.update("user", value, "name = ?", new String[] {user.name});
		//return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public boolean isExist(User user) {
		Cursor cursor = db.query("user", new String[] {"_id", "name"}, "name =?", new String[] {user.name}, null, null, null);
		if(cursor.getCount() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public ArrayList<HashMap<String, Object>> getUserList() {
		openDatabase();
		Cursor cursor = db.query("user", null, null, null, null, null, null);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		while(cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("_id", cursor.getInt(cursor.getColumnIndex("_id")));
			//map.put("imageid", cursor.getInt(cursor.getColumnIndex("imageid")));
			map.put("name", cursor.getString(cursor.getColumnIndex("name")));
			map.put("mobilephone", cursor.getString(cursor.getColumnIndex("mobilephone")));
			list.add(map);
			
			/*
			Set<String> keys = map.keySet();
			for(String key: keys){
				System.out.println(key + "--" + map.get(key));
			}
			*/
		}
		
		return list;
	}
}
