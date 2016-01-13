package com.example.httputil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.httputil.News.NewsType;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ReturnNewscontent {
	public NewsDto newsDto;

	public ReturnNewscontent() {
		// TODO Auto-generated constructor stub
		newsDto = new NewsDto();
	}

	public NewsDto getNews(String urlStr) throws CommonException {
		doGet(urlStr);
		while ((newsDto.newses.size() == 0)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newsDto;
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
						Looper.prepare();
						Handler handler = new Handler() {

							public void handleMessage(Message msg) {
								Log.i("456", "456");
								Log.i("url", msg.obj.toString());
								List<News> newses = new ArrayList<News>();
								Document doc = Jsoup.parse(msg.obj.toString());

								// 获得文章中的第一个detail
								Element detailEle = doc.select(".left .detail")
										.get(0);
								// 标题
								Element titleEle = detailEle.select("h1.title")
										.get(0);
								News news = new News();
								news.setTitle(titleEle.text());
								news.setType(NewsType.TITLE);
								newses.add(news);
								// 摘要
								Element summaryEle = detailEle.select(
										"div.summary").get(0);
								news = new News();
								news.setSummary(summaryEle.text());
								newses.add(news);
								// 内容
								Element contentEle = detailEle.select(
										"div.con.news_content").get(0);
								Elements childrenEle = contentEle.children();

								for (Element child : childrenEle) {
									Elements imgEles = child
											.getElementsByTag("img");
									// 图片
									if (imgEles.size() > 0) {
										for (Element imgEle : imgEles) {
											if (imgEle.attr("src").equals(""))
												continue;
											news = new News();
											news.setImageLink(imgEle
													.attr("src"));
											newses.add(news);
										}
									}
									// 移除图片
									imgEles.remove();

									if (child.text().equals(""))
										continue;

									news = new News();
									news.setType(NewsType.CONTENT);

									try {
										if (child.children().size() == 1) {
											Element cc = child.child(0);
											if (cc.tagName().equals("b")) {
												news.setType(NewsType.BOLD_TITLE);
											}
										}

									} catch (IndexOutOfBoundsException e) {
										e.printStackTrace();
									}
									news.setContent(child.outerHtml());
									newses.add(news);
								}
								newsDto.setNewses(newses);

							};
						};
						Looper.loop();
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
