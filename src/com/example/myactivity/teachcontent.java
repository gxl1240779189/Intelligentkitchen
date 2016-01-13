package com.example.myactivity;

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

import me.maxwin.view.XListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.adapter.FoodfuliaoAdapter;
import com.example.adapter.FoodteachAdapter;
import com.example.httputil.CommonException;
import com.example.httputil.FoodFuliao;
import com.example.httputil.Foodcontent;
import com.example.httputil.SliderShowViewItem;
import com.example.intelligentkitchenn.R;
import com.example.myapplication.myApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class teachcontent extends Activity implements
		android.view.View.OnClickListener {
	public ListView teachcontent_listview;
	public ListView fuliao_listview;
	public ProgressBar mProgressBar;
	public TextView title_textview;
	ImageView showfood;
	TextView showtitle;
	TextView showdetail;
	ImageView show_writer_image;
	TextView show_writer_name;
	TextView show_writer_date;
	TextView show_fuliao_tishi;
	TextView show_content_tishi;
	View show_xian;
	ImageView btn_back;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private int flag;
	List<FoodFuliao> foodfuliaos;
	List<Foodcontent> foodcontents;
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Log.i("url", msg.obj.toString());
			jiexi_shouji(msg.obj.toString());
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.teachcontent);
		teachcontent_listview = (ListView) findViewById(R.id.teachcontent__listview);
		fuliao_listview = (ListView) findViewById(R.id.fuliao__listview);
		mProgressBar = (ProgressBar) findViewById(R.id.teachcontent__newsContentPro);
		showfood = (ImageView) findViewById(R.id.showfood);
		show_writer_image = (ImageView) findViewById(R.id.show_writer_image);
		showtitle = (TextView) findViewById(R.id.showtitle);
		showdetail = (TextView) findViewById(R.id.showdetail);
		show_writer_name = (TextView) findViewById(R.id.show_writer_name);
		show_writer_date = (TextView) findViewById(R.id.show_writer_date);
		title_textview = (TextView) findViewById(R.id.headTV);
		show_content_tishi = (TextView) findViewById(R.id.show_content_tishi);
		show_fuliao_tishi = (TextView) findViewById(R.id.show_fuliao_tishi);
		show_xian = findViewById(R.id.show_xian);
		btn_back = (ImageView) findViewById(R.id.backBtn);
		foodfuliaos = new ArrayList<FoodFuliao>();
		foodcontents = new ArrayList<Foodcontent>();
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(teachcontent.this));
		btn_back.setOnClickListener(this);
		flag = getIntent().getExtras().getInt("flag");
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.xiaolian)
				.showImageForEmptyUri(R.drawable.xiaolian)
				.showImageOnFail(R.drawable.xiaolian).cacheInMemory()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		try {
			doGet(getIntent().getStringExtra("url"));
		} catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void jiexi_shouji(String canshu)
	{
		Document doc = Jsoup.parse(canshu);
		 String food_title = doc.getElementsByClass("fade_topbar").get(0)
		 .getElementsByTag("h2").get(0).text();
		 Log.i("food_title", food_title);
//		 String showfood_text = null;
		 String showfood_text = doc.getElementsByClass("cp_main").attr("style");
		 Log.i("showfood_text", showfood_text);
		 String NewStr=showfood_text.substring(showfood_text.indexOf("(")+1, showfood_text.lastIndexOf(")"));
		 Log.i("showfood_text", NewStr);

		 
//		  String showdetail_text =
//		 doc.getElementsByClass("materials").get(0)
//		  .getElementsByTag("p").get(0).text();
		 String show_writer_name_text = doc.getElementsByClass("con_main")
		 .get(0).getElementsByTag("a").get(0).getElementsByTag("span").text();
		
		 String show_writer_image_text = doc.getElementsByClass("con_main")
				 .get(0).getElementsByTag("a").get(0).getElementsByTag("img").attr("src");
//		 String show_writer_date_text = doc.getElementsByClass("user")
//		 .get(0).getElementsByClass("info").get(0)
//		 .getElementsByTag("strong").get(0).text();
//		 Log.i("tishi", food_title);
//		/*
//		 * 下面解析出做该菜要用到的食材和辅料
//		 */
		if (doc.select("div.material_coloum_type1").size() > 0) {
			Elements units = doc.getElementsByClass("material_coloum_type1");
			Elements units_zl = units.get(0).getElementsByClass("material_coloum");
			for (int i = 0; i < units_zl.size(); i++) {
				FoodFuliao fuliao = new FoodFuliao();
				Element content_zl_name = units_zl.get(i)			
						.getElementsByTag("span").get(0);
				String content_name = content_zl_name.text();
				Element content_zl_shuliang = units_zl.get(i)
						.getElementsByTag("em").get(0);
				String content_shuliang = content_zl_shuliang.text();
				fuliao.setFuliaoname(content_name);
				fuliao.setFuliaoshuliang(content_shuliang);
				Log.i("content_name", content_name);
				Log.i("content_shuliang", content_shuliang);
				foodfuliaos.add(fuliao);
			}
		}
		 
		if (doc.select("div.material_coloum_type2").size() > 0) {
			Elements units_fuliao = doc.getElementsByClass("material_coloum_type2");
			Elements Units_fuliao = units_fuliao.get(0).getElementsByClass("material_coloum");
			Log.i("content_fuliao_shuliang", Units_fuliao.size() + "");
			for (int i = 0; i < Units_fuliao.size(); i++) {
				FoodFuliao fuliao = new FoodFuliao();
				Element content_fuliao = Units_fuliao.get(i)
						.getElementsByTag("span").get(0);					
				String content_fuliao_name = content_fuliao.text();
				Element content_zl_shuliang = Units_fuliao.get(i)
						.getElementsByTag("em").get(0);
				String content_fuliao_shuliang = content_zl_shuliang.text();
				Log.i("content_fuliao_name", content_fuliao_name);
				Log.i("content_fuliao_shuliang", content_fuliao_shuliang);
				fuliao.setFuliaoname(content_fuliao_name);
				fuliao.setFuliaoshuliang(content_fuliao_shuliang);
				foodfuliaos.add(fuliao);
			}
		}
		fuliao_listview.setAdapter(new FoodfuliaoAdapter(myApplication
				.GetContext(), R.layout.foodfuliaoitem, foodfuliaos));

		 /**
		 * 下面解析出详细的做菜方法
		 */
		 if (doc.select("div.cp_step").size()>0) {
		 Elements units_teach = doc.getElementsByClass("cp_step").get(0).getElementsByTag("h2");
		 String teachtext;
		 String imagelink;
		 for (int i = 0; i < units_teach.size()-1; i++) {
		 Foodcontent foodcontent = new Foodcontent();	
		 Log.i("teachtext",  units_teach.get(i).text());
		 if(units_teach.get(i).nextElementSibling().getElementsByTag("img").size()>0)
		 {
			 imagelink=units_teach.get(i).nextElementSibling().getElementsByTag("img").get(0).attr("src");
			 teachtext=units_teach.get(i).nextElementSibling().nextElementSibling().text();
			 Log.i("imagelink", imagelink);
			 Log.i("teachtext", teachtext);
			  showfood_text = imagelink;
		 }else
		 {
			 imagelink="noimagelink";
			 teachtext=units_teach.get(i).nextElementSibling().nextElementSibling().text();
			 Log.i("imagelink", imagelink);
			 Log.i("teachtext", teachtext);
		 }	
		 foodcontent.setNum(units_teach.get(i).text());
		 foodcontent.setImagelink(imagelink);
		 foodcontent.setTeachtext(teachtext);
		 foodcontents.add(foodcontent);
		 }
		 }

		
		 teachcontent_listview
		 .setAdapter(new FoodteachAdapter(
		 myApplication.GetContext(),
		 R.layout.foodlistviewteachitem, foodcontents));
		 setfuliaoListViewHeightBasedOnChildren(fuliao_listview);
		 setListViewHeightBasedOnChildren(teachcontent_listview);
		 title_textview.setText(food_title);
		
		 imageLoader.displayImage(NewStr, showfood, options);
//		  showdetail.setText(showdetail_text);
		 showtitle.setText(food_title);
     	 show_writer_name.setText(show_writer_name_text);
//		 show_writer_date.setText(show_writer_date_text);
		 imageLoader.displayImage(show_writer_image_text,
		 show_writer_image,
		 options);
		
		 showfood.setVisibility(View.VISIBLE);
		 show_writer_image.setVisibility(View.VISIBLE);
		 showtitle.setVisibility(View.VISIBLE);
//		 showdetail.setVisibility(View.VISIBLE);
		 show_writer_name.setVisibility(View.VISIBLE);
		 show_writer_date.setVisibility(View.VISIBLE);
		 show_content_tishi.setVisibility(View.VISIBLE);
		 show_fuliao_tishi.setVisibility(View.VISIBLE);
		 show_xian.setVisibility(View.VISIBLE);
		 mProgressBar.setVisibility(View.GONE);
	}

	public void jiexi_wangye(String canshu)
	{
		Document doc = Jsoup.parse(canshu);
		 String food_title = doc.getElementsByClass("info1").get(0)
		 .getElementsByTag("a").get(0).text();
		 String showfood_text = doc.getElementsByClass("cp_headerimg_w")
		 .get(0).getElementsByTag("img").get(0).attr("src");
		  String showdetail_text =
		 doc.getElementsByClass("materials").get(0)
		  .getElementsByTag("p").get(0).text();
		 String show_writer_name_text = doc.getElementsByClass("user")
		 .get(0).getElementsByClass("info").get(0)
		 .getElementsByTag("h4").get(0).getElementsByTag("a").get(0)
		 .text();
		 String show_writer_image_text = doc.getElementsByClass("user")
		 .get(0).getElementsByTag("a").get(0)
		 .getElementsByTag("img").get(0).attr("src");
		 String show_writer_date_text = doc.getElementsByClass("user")
		 .get(0).getElementsByClass("info").get(0)
		 .getElementsByTag("strong").get(0).text();
		 Log.i("tishi", food_title);
		/*
		 * 下面解析出做该菜要用到的食材和辅料
		 */
		if (doc.select("div.zl").size() > 0) {
			Elements units = doc.getElementsByClass("zl");
			Elements units_zl = units.get(0).getElementsByTag("li");
			for (int i = 0; i < units_zl.size(); i++) {
				FoodFuliao fuliao = new FoodFuliao();
				Element content_zl_name = units_zl.get(i)
						.getElementsByClass("c").get(0)
						.getElementsByTag("h4").get(0)
						.getElementsByTag("a").get(0);
				String content_name = content_zl_name.text();
				Element content_zl_shuliang = units_zl.get(i)
						.getElementsByClass("c").get(0)
						.getElementsByTag("h4").get(0)
						.getElementsByTag("span").get(0);
				String content_shuliang = content_zl_shuliang.text();
				fuliao.setFuliaoname(content_name);
				fuliao.setFuliaoshuliang(content_shuliang);
				Log.i("content_name", content_name);
				Log.i("content_shuliang", content_shuliang);
				foodfuliaos.add(fuliao);
			}
		}
		if (doc.select("div.fuliao").size() > 0) {
			Elements units_fuliao = doc.getElementsByClass("fuliao");
			Elements Units_fuliao = units_fuliao.get(0).getElementsByTag(
					"li");
			Log.i("content_fuliao_shuliang", Units_fuliao.size() + "");
			for (int i = 0; i < Units_fuliao.size(); i++) {
				FoodFuliao fuliao = new FoodFuliao();
				Element content_fuliao = Units_fuliao.get(i)
						.getElementsByTag("h4").get(0)
						.getElementsByTag("a").get(0);
				String content_fuliao_name = content_fuliao.text();
				Element content_zl_shuliang = Units_fuliao.get(i)
						.getElementsByTag("span").get(0);
				String content_fuliao_shuliang = content_zl_shuliang.text();
				Log.i("content_fuliao_name", content_fuliao_name);
				Log.i("content_fuliao_shuliang", content_fuliao_shuliang);
				fuliao.setFuliaoname(content_fuliao_name);
				fuliao.setFuliaoshuliang(content_fuliao_shuliang);
				foodfuliaos.add(fuliao);
			}
		}
		fuliao_listview.setAdapter(new FoodfuliaoAdapter(myApplication
				.GetContext(), R.layout.foodfuliaoitem, foodfuliaos));

		// /**
		// * 下面解析出详细的做菜方法
		// */
		 if (doc.select("div.content").size()>0) {
		 Log.i("1111", "1111");
		 Elements units_teach = doc.getElementsByClass("measure").get(0)
		 .getElementsByClass("edit").get(0)
		 .getElementsByClass("content");
		 Log.i("units_teach", units_teach.size() + "");
		 String teachtext;
		 String imagelink;
		 for (int i = 0; i < units_teach.size(); i++) {
		 Foodcontent foodcontent = new Foodcontent();
		
		 if (units_teach.get(i).getElementsByClass("c").get(0)
		 .getElementsByTag("p").size() == 2) {
		 teachtext = units_teach.get(i).getElementsByClass("c")
		 .get(0).getElementsByTag("p").get(0).text();
		 imagelink = units_teach.get(i).getElementsByClass("c")
		 .get(0).getElementsByTag("p").get(1)
		 .getElementsByTag("img").get(0).attr("src");
		 } else {
		 if (units_teach.get(i).getElementsByClass("c").get(0)
		 .getElementsByTag("p").get(0).hasAttr("src")) {
		 teachtext = "";
		 imagelink = units_teach.get(i)
		 .getElementsByClass("c").get(0)
		 .getElementsByTag("p").get(0)
		 .getElementsByTag("img").get(0).attr("src");
		 } else {
		 teachtext = units_teach.get(i)
		 .getElementsByClass("c").get(0)
		 .getElementsByTag("p").get(0).text();
		 imagelink = "noimagelink";
		 }
		 }
		
		 Log.i("teachtext", teachtext);
		 Log.i("imagelink", imagelink);
		 foodcontent.setNum(String.valueOf(i + 1));
		 foodcontent.setImagelink(imagelink);
		 foodcontent.setTeachtext(teachtext);
		 foodcontents.add(foodcontent);
		 }
		 } else {
		 Log.i("2222", "2222");
		 Elements units_teach = doc.getElementsByClass("measure").get(0)
		 .getElementsByClass("edit").get(0)
		 .getElementsByTag("p");
		 Log.i("units_teach", units_teach.size() + "");
		 Log.i("units_teach_texy", units_teach.get(0).text());
		 int num=0;
		 for (int i = 0, j = 0; i < units_teach.size() - 1; i++, j++) {
		 Foodcontent foodcontent = new Foodcontent();
		 String teachtext = null;
		 String imagelink = null;
		 if (units_teach.get(i).getElementsByTag("em").size() > 0) {
		 teachtext = units_teach.get(i).text();
		 int flag = i + 1;
		 if (units_teach.get(flag).getElementsByClass("conimg")
		 .size() > 0) {
		 imagelink = units_teach.get(flag)
		 .getElementsByClass("conimg").get(0)
		 .attr("src");
		 i++;
		 } else {
		 imagelink = "noimagelink";
		 }
		 } else if (units_teach.get(i).getElementsByTag("img")
		 .size() > 0) {
		 teachtext = "";
		 imagelink = units_teach.get(i)
		 .getElementsByClass("conimg").get(0)
		 .attr("src");
		 }else
		 {
		 continue;
		 }
		 Log.i("teachtext", teachtext);
		 Log.i("imagelink", imagelink);
		 foodcontent.setNum(String.valueOf(++num));
		 foodcontent.setImagelink(imagelink);
		 foodcontent.setTeachtext(teachtext);
		 foodcontents.add(foodcontent);
		 }
		 }
		
		 teachcontent_listview
		 .setAdapter(new FoodteachAdapter(
		 myApplication.GetContext(),
		 R.layout.foodlistviewteachitem, foodcontents));
		 setfuliaoListViewHeightBasedOnChildren(fuliao_listview);
		 setListViewHeightBasedOnChildren(teachcontent_listview);
		 title_textview.setText(food_title);
		
		 imageLoader.displayImage(showfood_text, showfood, options);
		 // showdetail.setText(showdetail_text);
		 showtitle.setText(food_title);
		 show_writer_name.setText(show_writer_name_text);
		 show_writer_date.setText(show_writer_date_text);
		 imageLoader.displayImage(show_writer_image_text,
		 show_writer_image,
		 options);
		
		 showfood.setVisibility(View.VISIBLE);
		 show_writer_image.setVisibility(View.VISIBLE);
		 showtitle.setVisibility(View.VISIBLE);
		 // showdetail.setVisibility(View.VISIBLE);
		 show_writer_name.setVisibility(View.VISIBLE);
		 show_writer_date.setVisibility(View.VISIBLE);
		 show_content_tishi.setVisibility(View.VISIBLE);
		 show_fuliao_tishi.setVisibility(View.VISIBLE);
		 show_xian.setVisibility(View.VISIBLE);
		 mProgressBar.setVisibility(View.GONE);
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
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is, "UTF-8"));
						int len = 0;
						String line;
						while ((line = reader.readLine()) != null) {
							sb.append(line);
						}
						Message message = new Message();
						message.obj = sb;
						Log.i("ccc", sb.toString());
						FileOutputStream fos = null;
						try {
							fos = myApplication.GetContext().openFileOutput(
									"food.html", 0);
							try {
								fos.write(sb.toString().getBytes());
								fos.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							if (fos != null) {
								try {
									fos.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						Looper.prepare();
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

	/**
	 * 动态设置ListView的高度
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += (listItem.getMeasuredHeight() + 100);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ +(listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public void setfuliaoListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ +(listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backBtn:
			finish();
			break;
		default:
			break;
		}
	}

}
