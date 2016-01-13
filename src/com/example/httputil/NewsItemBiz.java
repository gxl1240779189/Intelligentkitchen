package com.example.httputil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.myapplication.myApplication;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract.Document;
import android.util.Log;

public class NewsItemBiz {

	List<NewsItem> newsItems = new ArrayList<NewsItem>();
	private int newsType;

	public NewsItemBiz() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 在这个handler里面解析html文件的内容，并将其存储到newsItems数组中去。
	 * 
	 */
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			NewsItem newsItem = null;
			org.jsoup.nodes.Document doc = Jsoup.parse(msg.obj.toString());
			Elements units = doc.getElementsByClass("unit");
			for (int i = 0; i < units.size(); i++) {
				newsItem = new NewsItem();
				newsItem.setNewsType(newsType);

				Element unit_ele = units.get(i);

				Element h1_ele = unit_ele.getElementsByTag("h1").get(0);
				Element h1_a_ele = h1_ele.child(0);
				String title = h1_a_ele.text();
				String href = h1_a_ele.attr("href");
				Log.i("hello", title);
				newsItem.setLink(href);
				newsItem.setTitle(title);

				Element h4_ele = unit_ele.getElementsByTag("h4").get(0);
				Element ago_ele = h4_ele.getElementsByClass("ago").get(0);
				String date = ago_ele.text();

				newsItem.setDate(date);

				Element dl_ele = unit_ele.getElementsByTag("dl").get(0);// dl
				Element dt_ele = dl_ele.child(0);// dt
				try {// 可能没有图片
					Element img_ele = dt_ele.child(0);
					String imgLink = img_ele.child(0).attr("src");
					newsItem.setImgLink(imgLink);
				} catch (IndexOutOfBoundsException e) {

				}
				Element content_ele = dl_ele.child(1);// dd
				String content = content_ele.text();
				newsItem.setContent(content);
			
				newsItems.add(newsItem);
			}

		};
	};

	public List<NewsItem> getNewsItems(int newsType, int currentPage)
			throws CommonException, IOException {
		this.newsType=newsType;
		Log.i("kanyikan", this.newsType+"");
		String urlStr = URLUtil.generateUrl(newsType, currentPage);
		newsItems.clear();
		doGet(urlStr);
		Log.i("hello", "123");
		while ((newsItems.size() == 0)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i("hello", "456");
		return newsItems;

	}

	public void doGet(final String urlStr) throws CommonException {
		final StringBuffer sb = new StringBuffer();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					URL url = new URL(urlStr);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					conn.setDoInput(true);
					conn.setDoOutput(true);
					if (conn.getResponseCode() == 200) {
						InputStream is = conn.getInputStream();
						int len = 0;
						byte[] buf = new byte[1024];
						while ((len = is.read(buf)) != -1) {
							sb.append(new String(buf, 0, len, "UTF-8"));
						}
						Message message = new Message();
						message.obj = sb;
						handler.sendMessage(message);
						is.close();
					} else {
						throw new CommonException("访问网络失败00");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						throw new CommonException("访问网络失败11");
					} catch (CommonException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}).start();
	}
}
