package com.example.myactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.example.intelligentkitchen.R;
import com.example.intelligentkitchen.R.id;
import com.example.myinterface.MysocketInterface;
import com.example.other.Mysocket;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class kaiguanshebeiActivity extends Activity implements OnClickListener {
	public Button yaokong;
	private Socket mysocket = null;
	private InputStream in;
	public boolean isConnected = false;
	Thread receiverThread;
	PrintWriter printWriter = null;
	BufferedReader reader;
	public TextView shezhi;
	public ArrayList<View> list;
	public Button open_weibolu;
	public Button close_webolu;
	public Button open_youyanji;
	public Button close_youyanji;
	public Button open_meiqizhao;
	public Button close_meiqizhao;
	public Button open_dengguang;
	public Button close_dengguang;
	private TextView kaiguanshebei;
	private ViewPager viewpaper;

	private ImageView imageView;// 动画图片
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	/**
	 * 开关设备的有:微波炉(1开,2关)，抽油烟机(3开,4关)，煤气灶(5开，6关),灯光(7开，8关).
	 * 
	 */
	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				String result = msg.getData().get("msg").toString();
				Log.i("来自服务器的消息", "result");
			} else if (msg.what == 2) {
				// receiverThread = new Thread(new MyReceiverRunnable());
				// receiverThread.start();
				Log.i("tcpip", "receive success");
				isConnected = true;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yaokong);
		InitImageView();
		list = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		View view_weibolu = mInflater.inflate(R.layout.weibolu, null);
		View view_youyanji = mInflater.inflate(R.layout.youyanji, null);
		View view_meiqizhao = mInflater.inflate(R.layout.meiqizhao, null);
		View view_dengguang = mInflater.inflate(R.layout.dengguang, null);
		list.add(view_weibolu);
		list.add(view_youyanji);
		list.add(view_meiqizhao);
		list.add(view_dengguang);
		ViewPager viewpaper = (ViewPager) findViewById(R.id.viewpager);
		viewpaper.setAdapter(new ViewPagerAdapter(list));
		viewpaper.setOnPageChangeListener(new MyOnPageChangeListener());
		open_weibolu = (Button) view_weibolu.findViewById(R.id.open_weibolu);
		close_webolu = (Button) view_weibolu.findViewById(R.id.close_weibolu);
		open_youyanji = (Button) view_youyanji.findViewById(id.open_youyanji);
		close_youyanji = (Button) view_youyanji
				.findViewById(R.id.close_youyanji);
		open_meiqizhao = (Button) view_meiqizhao
				.findViewById(R.id.open_meiqizhao);
		close_meiqizhao = (Button) view_meiqizhao
				.findViewById(R.id.close_meiqizhao);
		open_dengguang = (Button) view_dengguang
				.findViewById(R.id.open_dengguang);
		close_dengguang = (Button) view_dengguang
				.findViewById(R.id.close_dengguang);
		open_weibolu.setOnClickListener(this);
		close_webolu.setOnClickListener(this);
		open_youyanji.setOnClickListener(this);
		close_youyanji.setOnClickListener(this);
		open_meiqizhao.setOnClickListener(this);
		close_meiqizhao.setOnClickListener(this);
		open_dengguang.setOnClickListener(this);
		close_dengguang.setOnClickListener(this);
	}

	public class ViewPagerAdapter extends PagerAdapter {

		// 界面列表
		private ArrayList<View> views;

		public ViewPagerAdapter(ArrayList<View> views) {
			this.views = views;
		}

		// 获得当前界面总数
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (views != null) {
				return views.size();
			}
			return 0;
		}

		// 初始化position位置的界面
		@Override
		public Object instantiateItem(View view, int position) {
			// TODO Auto-generated method stub
			((ViewPager) view).addView(views.get(position), 0);
			return views.get(position);
		}

		// 判断是否由对象生成界面
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;

		}

		// 销毁position位置的界面
		@Override
		public void destroyItem(View view, int position, Object arg2) {
			((ViewPager) view).removeView(views.get(position));
		}

	}

	// private class MyReceiverRunnable implements Runnable {
	//
	// public void run() {
	//
	// while (true) {
	// if (isConnected) {
	// if (mysocket != null && mysocket.isConnected()) {
	//
	// String result = readFromInputStream(in);
	//
	// try {
	// if (!result.equals("")) {
	// Message msg = new Message();
	// msg.what = 1;
	// Bundle data = new Bundle();
	// data.putString("msg", result);
	// msg.setData(data);
	// myHandler.sendMessage(msg);
	// }
	//
	// } catch (Exception e) {
	//
	// }
	// }
	// }
	// try {
	// Thread.sleep(100L);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

	public String readFromInputStream(InputStream in) {
		int count = 0;
		byte[] inDatas = null;
		try {
			while (count == 0) {
				count = in.available();
			}
			inDatas = new byte[count];
			in.read(inDatas);
			return new String(inDatas, "gb2312");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送消息的方法
	 */
	public void send(String msg) {
		try {
			PrintStream ps = new PrintStream(mysocket.getOutputStream());
			ps.println(msg);
			ps.flush();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.open_weibolu:
			if (Mysocket.getState() == 1) {
				mysocket = Mysocket.getSocket();
				send("1");
			} else {
				Toast.makeText(kaiguanshebeiActivity.this,
						"当前wifi尚未连接，请到设置页面中连接!", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.close_weibolu:
			if (Mysocket.getState() == 1) {
				mysocket = Mysocket.getSocket();
				send("2");
			} else {
				Toast.makeText(kaiguanshebeiActivity.this,
						"当前wifi尚未连接，请到设置页面中连接!", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.open_youyanji:
			if (Mysocket.getState() == 1) {
				mysocket = Mysocket.getSocket();
				send("3");
			} else {
				Toast.makeText(kaiguanshebeiActivity.this,
						"当前wifi尚未连接，请到设置页面中连接!", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.close_youyanji:
			if (Mysocket.getState() == 1) {
				mysocket = Mysocket.getSocket();
				send("4");
			} else {
				Toast.makeText(kaiguanshebeiActivity.this,
						"当前wifi尚未连接，请到设置页面中连接!", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.open_meiqizhao:
			if (Mysocket.getState() == 1) {
				mysocket = Mysocket.getSocket();
				send("5");
			} else {
				Toast.makeText(kaiguanshebeiActivity.this,
						"当前wifi尚未连接，请到设置页面中连接!", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.close_meiqizhao:
			if (Mysocket.getState() == 1) {
				mysocket = Mysocket.getSocket();
				send("6");
			} else {
				Toast.makeText(kaiguanshebeiActivity.this,
						"当前wifi尚未连接，请到设置页面中连接!", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.open_dengguang:
			if (Mysocket.getState() == 1) {
				mysocket = Mysocket.getSocket();
				send("7");
			} else {
				Toast.makeText(kaiguanshebeiActivity.this,
						"当前wifi尚未连接，请到设置页面中连接!", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.close_dengguang:
			if (Mysocket.getState() == 1) {
				mysocket = Mysocket.getSocket();
				send("8");
			} else {
				Toast.makeText(kaiguanshebeiActivity.this,
						"当前wifi尚未连接，请到设置页面中连接!", Toast.LENGTH_SHORT).show();
			}
			break;

		}
	}

	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */

	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
			// Toast.makeText(this, "您选择了"+ viewpager.getCurrentItem()+"页卡",
			// Toast.LENGTH_SHORT).show();
		}
	}
}
