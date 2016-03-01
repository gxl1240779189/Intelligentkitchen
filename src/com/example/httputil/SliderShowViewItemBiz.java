package com.example.httputil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.example.myapplication.myApplication;

public class SliderShowViewItemBiz {
	public List<SliderShowViewItem> ItemList = new ArrayList<SliderShowViewItem>();

	public SliderShowViewItemBiz() {
	}

	public void jsoup_jiexi(String msg) {
		SliderShowViewItem viewItem = null;
		org.jsoup.nodes.Document doc = Jsoup.parse(msg);
		// Elements units = doc.getElementsByClass("slider");
		Elements content = doc.getElementsByClass("zzw_item_2");
		Elements links = content.get(0).getElementsByTag("li");
		// Elements links_a = links.get(0).getElementsByTag("a");
		Log.i("msg", msg);
		Log.i("content.size()", content.size() + "");
		Log.i("links.size()", links.size() + "");

		for (int i = 0; i < links.size(); i++) {
			viewItem = new SliderShowViewItem();
			Elements links_a = links.get(i).getElementsByTag("a");
			String link = links_a.get(0).attr("href");
			Element imagelink_ele = links_a.get(0).getElementsByTag("img")
					.get(0);
			String imagelink = imagelink_ele.attr("src");
			String foodname = links_a.get(0).attr("title");
			viewItem.setImgLink(imagelink);
			viewItem.setLink(link);
			viewItem.setFoodname(foodname);
			Log.i("link", link);
			Log.i("imagelink", imagelink);
			Log.i("foodname", foodname);
			ItemList.add(viewItem);
		}
	}

	public List<SliderShowViewItem> getNewsItems(String urlStr)
			throws CommonException, IOException {
		ItemList.clear();
		doGet(urlStr);
		Log.i("hello", "123");
		while (ItemList.size() == 0) {
			try {
				Thread.sleep(2000);
				Log.i("hello", "888");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i("hello", "456");
		return ItemList;

	}

	public void doGet(final String urlStr) throws CommonException {
		final StringBuffer sb = new StringBuffer();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Log.i("msg", "123");
					URL url = new URL(urlStr);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					conn.setDoInput(true);
					conn.setDoOutput(true);
					if (conn.getResponseCode() == 200) {
						InputStream is = conn.getInputStream();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is, "UTF-8"));
						int len = 0;
						String line;
						while ((line = reader.readLine()) != null) {
							sb.append(line);
						}
						jsoup_jiexi(sb.toString());
						is.close();
					} else {
						throw new CommonException("∑√Œ Õ¯¬Á ß∞‹00");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						throw new CommonException("∑√Œ Õ¯¬Á ß∞‹11");
					} catch (CommonException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}).start();
	}

}
