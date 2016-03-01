package com.example.db;

import java.util.ArrayList;
import java.util.List;

import com.example.httputil.FoodsItem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FoodsItemDao {
	private MyDatabaseHelper dbHelper;

	public FoodsItemDao(Context context) {
		dbHelper = new MyDatabaseHelper(context);
	}

	public void add(FoodsItem foodsItem) {
		String sql = "insert into love_Fooditem (title,link,date,imgLink,content,writer) values(?,?,?,?,?,?);";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL(
				sql,
				new Object[] { foodsItem.getTitle(), foodsItem.getLink(),
						foodsItem.getDate(), foodsItem.getImgLink(),
						foodsItem.getContent(), foodsItem.getWriter() });
		db.close();
	}
	
	public void delete(FoodsItem foodsItem) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("love_Fooditem", "title = ?", new String[]{foodsItem.getTitle()});
   	    db.close();
	}
	
	public synchronized List<FoodsItem> list() {

		List<FoodsItem> foodsItems = new ArrayList<FoodsItem>();
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.query("love_Fooditem", null, null, null, null, null, null);
			FoodsItem foodsItem = null;
//			 " title text , link text , date text , imgLink text , content text , writer text
			while (c.moveToNext()) {
				foodsItem = new FoodsItem();
				String title = c.getString(1);
				String link = c.getString(2);
				String date = c.getString(3);
				String imgLink = c.getString(4);
				String content = c.getString(5);
				String writer = c.getString(6);

				foodsItem.setTitle(title);
				foodsItem.setLink(link);
				foodsItem.setImgLink(imgLink);
				foodsItem.setDate(date);
				foodsItem.setWriter(writer);
				foodsItem.setContent(content);
				foodsItems.add(foodsItem);

			}
			c.close();
			db.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foodsItems;

	}

 }
