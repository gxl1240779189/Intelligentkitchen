package com.example.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.httputil.NewsItem;

public class NewsItemDao {
	private MyDatabaseHelper dbHelper;

	public NewsItemDao(Context context) {
		dbHelper = new MyDatabaseHelper(context);
	}

	public void add(NewsItem newsItem) {
		String sql = "insert into tb_newsItem (title,link,date,imgLink,content,newstype) values(?,?,?,?,?,?) ;";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL(
				sql,
				new Object[] { newsItem.getTitle(), newsItem.getLink(),
						newsItem.getDate(), newsItem.getImgLink(),
						newsItem.getContent(), newsItem.getNewsType() });
		db.close();
	}

	public void deleteAll(int newsType) {
		String sql = "delete from tb_newsItem where newstype = ?";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL(sql, new Object[] { newsType });
		db.close();
	}

	public void add(List<NewsItem> newsItems) {
		for (NewsItem newsItem : newsItems) {
			add(newsItem);
		}
	}

	/**
	 * 根据newsType和currentPage从数据库中取数据
	 * 
	 * @param newsType
	 * @param currentPage
	 * @return
	 */
	public synchronized List<NewsItem> list(int newsType, int currentPage) {

		List<NewsItem> newsItems = new ArrayList<NewsItem>();
		try {
			int offset = 10 * (currentPage - 1);
			String sql = "select title,link,date,imgLink,content,newstype from tb_newsItem where newstype = ? limit ?,? ";
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery(sql, new String[] { newsType + "",
					offset + "", "" + (offset + 10) });

			NewsItem newsItem = null;

			while (c.moveToNext()) {
				newsItem = new NewsItem();

				String title = c.getString(0);
				String link = c.getString(1);
				String date = c.getString(2);
				String imgLink = c.getString(3);
				String content = c.getString(4);
				Integer newstype = c.getInt(5);

				newsItem.setTitle(title);
				newsItem.setLink(link);
				newsItem.setImgLink(imgLink);
				newsItem.setDate(date);
				newsItem.setNewsType(newstype);
				newsItem.setContent(content);

				newsItems.add(newsItem);

			}
			c.close();
			db.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newsItems;

	}

}
