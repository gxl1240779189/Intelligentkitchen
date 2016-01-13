package com.example.httputil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.myapplication.myApplication;

import android.graphics.AvoidXfermode.Mode;
import android.os.Handler;
import android.os.Message;
import android.provider.OpenableColumns;
import android.util.Log;

public class FoodsItemBiz {
	public List<FoodsItem> foodsItems = new ArrayList<FoodsItem>();
	private int leixing;

	public FoodsItemBiz() {
		// TODO Auto-generated constructor stub
	}

	public void jsoup_jiexi(String msg) {
		FoodsItem foodsItem = null;
		FileOutputStream fos = null;
		org.jsoup.nodes.Document doc = Jsoup.parse(msg);
		Log.i("msg", msg);
		Elements units = doc.getElementsByClass("listtyle1");
		Elements detail_units = doc.getElementsByClass("c1");
		Elements date_units = doc.getElementsByClass("c2");
		Log.i("units", units.size() + "");
		Log.i("detail_units", detail_units.size() + "");
		for (int i = 0; i < units.size(); i++) {
			foodsItem = new FoodsItem();
			Element detail_ele = units.get(i);
			Elements links = detail_ele.getElementsByTag("a");
			String link = links.get(0).attr("href");
			String title = links.get(0).attr("title");
			Element imagelink_ele = detail_ele.getElementsByTag("img").get(0);
			String imagelink = imagelink_ele.attr("src");

			Element content_ele = detail_units.get(i);
			Elements content_pinglus = detail_ele.getElementsByTag("span");
			String content_pinglun = content_pinglus.get(0).text();
			Elements content_pername = detail_ele.getElementsByTag("em");
			String content_personame = content_pername.get(0).text();

			Element date_ele = date_units.get(i);
			Elements date_shijian = detail_ele.getElementsByTag("ul");
			String content_bushu = date_shijian.get(0)
					.getElementsByClass("li1").text();
			String content_weidao = date_shijian.get(0)
					.getElementsByClass("li2").text();

			String content = "    " + content_weidao + "  |  "
					+ content_pinglun;
			String Personname = content_personame;
			String Date = content_bushu;
			foodsItem.setTitle(title);
			foodsItem.setLink(link);
			foodsItem.setWriter(Personname);
			foodsItem.setDate(Date);
			foodsItem.setImgLink(imagelink);
			foodsItem.setContent(content);

			Log.i("title", title);
			Log.i("link", link);
			Log.i("Date", Date);
			Log.i("personname", Personname);
			Log.i("imagelink", imagelink);
			Log.i("content", content);
			foodsItems.add(foodsItem);
		}
	}

	public void jsoup_jiexi_search(String msg) {
		FoodsItem foodsItem = null;
		FileOutputStream fos = null;
		org.jsoup.nodes.Document doc = Jsoup.parse(msg);
		Log.i("msg", msg);
		Elements units = doc.getElementsByClass("search2015_cpitem");
		Elements detail_units = doc.getElementsByClass("i");
		Log.i("units", units.size() + "");
		Log.i("detail_units", detail_units.size() + "");
		for (int i = 0; i < units.size(); i++) {
			foodsItem = new FoodsItem();
			Element detail_ele = units.get(i);
			Elements links = detail_ele.getElementsByTag("a");
			String link = links.get(0).attr("href");
			String title = links.get(0).attr("title");
			Element imagelink_ele = detail_ele.getElementsByTag("img").get(0);
			String imagelink = imagelink_ele.attr("src");

			// Elements content_pinglus = detail_ele.getElementsByTag("span");
			// String content_pinglun = content_pinglus.get(0).text();
			// Elements content_pername = detail_ele.getElementsByTag("em");
			// String content_personame = content_pername.get(0).text();
			//
			// Elements date_shijian = detail_ele.getElementsByTag("ul");
			// String content_bushu = date_shijian.get(0)
			// .getElementsByClass("li1").text();
			// String content_weidao = date_shijian.get(0)
			// .getElementsByClass("li2").text();
			//
			// String content = "    " + content_weidao + "  |  "
			// + content_pinglun;
			// String Personname = content_personame;
			// String Date = content_bushu;
			foodsItem.setTitle(title);
			foodsItem.setLink(link);
			foodsItem.setWriter("qqq");
			foodsItem.setDate("qqq");
			foodsItem.setImgLink(imagelink);
			foodsItem.setContent("qqq");

			// Log.i("title", title);
			// Log.i("link", link);
			// Log.i("Date", Date);
			// Log.i("personname", Personname);
			// Log.i("imagelink", imagelink);
			// Log.i("content", content);
			foodsItems.add(foodsItem);
		}
	}

	public List<FoodsItem> getNewsItems(String urlStr, int leixing)
			throws CommonException, IOException {
		foodsItems.clear();
		this.leixing = leixing;
		doGet(urlStr);
		Log.i("hello", "123");
		while (foodsItems.size() == 0) {
			try {
				Thread.sleep(1000);
				Log.i("foodsItems.size()", foodsItems.size() + "111");
				Log.i("hello", "999");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i("hello", "456");
		return foodsItems;

	}

	public List<FoodsItem> getNewsItems1(String urlStr) throws CommonException,
			IOException {
		foodsItems.clear();
		doGet(urlStr);
		Log.i("hello", "123");
		while (foodsItems.size() == 0) {
			try {
				Thread.sleep(1000);
				Log.i("foodsItems.size()", foodsItems.size() + "111");
				Log.i("hello", "999");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i("hello", "456");
		return foodsItems;

	}

	public void doGet(final String urlStr) throws CommonException {
		final StringBuffer sb = new StringBuffer();

		new Thread(new Runnable() {
			HttpURLConnection conn = null;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					URL url = new URL(urlStr);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestProperty("Charset", "UTF-8");
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					conn.setDoInput(true);
					conn.setDoOutput(true);
					if (conn.getResponseCode() == 200) {
						InputStream is = conn.getInputStream();
						BufferedReader reader=new BufferedReader(new InputStreamReader(is, "UTF-8"));
						int len = 0;
//						byte[] buf = new byte[60000];
//						while ((len = is.read(buf)) != -1) {
//							sb.append(new String(buf, 0, len, "UTF-8"));
//						}
						String line;
						while ((line = reader.readLine()) != null) {
							sb.append(line);
						}
						if (leixing == 0) {
							jsoup_jiexi(sb.toString());
						} else {
							jsoup_jiexi_search(sb.toString());
						}
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
				} finally {
					conn.disconnect();
				}
			}
		}).start();
	}

	public void doGet1(final String urlStr) throws CommonException {
		final StringBuffer sb = new StringBuffer();

		new Thread(new Runnable() {
			HttpURLConnection conn = null;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					URL url = new URL(urlStr);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestProperty("Charset", "UTF-8");
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					conn.setDoInput(true);
					conn.setDoOutput(true);
					if (conn.getResponseCode() == 200) {
						InputStream is = conn.getInputStream();
						int len = 0;
						byte[] buf = new byte[60000];
						while ((len = is.read(buf)) != -1) {
							sb.append(new String(buf, 0, len, "UTF-8"));
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
				} finally {
					conn.disconnect();
				}
			}
		}).start();
	}

}
