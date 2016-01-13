package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	private Context context;
	private static final String DB_NAME = "csdn_app_demo";

	public MyDatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table tb_newsItem( _id integer primary key autoincrement , "
				+ " title text , link text , date text , imgLink text , content text , newstype integer  );";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub 

	}
	


}
