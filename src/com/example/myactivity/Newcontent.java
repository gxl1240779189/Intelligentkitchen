package com.example.myactivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.XListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.adapter.NewscontentAdapter;
import com.example.httputil.CommonException;
import com.example.httputil.News;
import com.example.httputil.NewsDto;
import com.example.httputil.NewsItemBiz;
import com.example.httputil.ReturnNewscontent;
import com.example.httputil.News.NewsType;


import com.example.intelligentkitchenn.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Newcontent extends Activity implements IXListViewLoadMore {
	public ReturnNewscontent returnnewscontent;
	public NewsDto newsdto;
	public List<News> list;
	public News news;
	private XListView mListView;

	/**
	 * ∏√“≥√Êµƒurl
	 */
	private String url;
	private NewsItemBiz mNewsItemBiz;
	private List<News> mDatas;

	private ProgressBar mProgressBar;
	private NewscontentAdapter mAdapter;
	public Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			Log.i("url", msg.obj.toString());
			List<News> newses = new ArrayList<News>();
			Document doc = Jsoup.parse(msg.obj.toString());
			Elements units = doc.getElementsByClass("wrapper");
			String title = units.get(0).select("h1").text();
			News news = new News();
			news.setTitle(title);
			news.setType(NewsType.TITLE);
			newses.add(news);
			String context = units.get(0).getElementsByClass("infor")
					.select("span").text();
			Log.i("title", title);
			Elements childrenEle = units.get(0).getElementsByClass("text")
					.select("p");
			int i = childrenEle.size();
			Log.i("size", String.valueOf(i));

			for (Element child : childrenEle) {
				Elements imgEles = child.getElementsByTag("img");
				// Õº∆¨
				if (imgEles.size() > 0) {
					for (Element imgEle : imgEles) {
						if (imgEle.attr("src").equals(""))
							continue;
						news = new News();
						news.setImageLink(imgEle.attr("src"));
						newses.add(news);
					}
				}
				// “∆≥˝Õº∆¨
				imgEles.remove();

				if (child.text().equals(""))
					continue;

				news = new News();
				news.setType(NewsType.CONTENT);

				try {
					Element cc = child.child(0);
					if (cc.tagName().equals("b")) {
						news.setType(NewsType.BOLD_TITLE);
					}
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				news.setContent(child.outerHtml());
				newses.add(news);
			}
			for (News element : newses) {
				Log.i("news", element.toString());
			}
			// newsdto.setNewses(newses);
			mAdapter.addList(newses);
			mAdapter.notifyDataSetChanged();
			mProgressBar.setVisibility(View.GONE);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		returnnewscontent = new ReturnNewscontent();
		newsdto = new NewsDto();
		list = new ArrayList<News>();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_content);
		mNewsItemBiz = new NewsItemBiz();

		mAdapter = new NewscontentAdapter(this);

		mListView = (XListView) findViewById(R.id.id_listview);
		mProgressBar = (ProgressBar) findViewById(R.id.id_newsContentPro);

		mListView.setAdapter(mAdapter);
		mListView.disablePullRefreash();
		mListView.setPullLoadEnable(this);

		test();

	}

	public void test() {		
		try {
			
			doGet(getIntent().getStringExtra("url"));

		} catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void back(View view) {
		// TODO Auto-generated method stub
		finish();
	}

	public void doGet(final String urlStr) throws CommonException {
		final StringBuffer sb = new StringBuffer();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.i("123", "123");
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
						Looper.prepare();
						handler.sendMessage(message);
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

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}
}
