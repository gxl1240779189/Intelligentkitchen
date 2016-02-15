package com.example.myfragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.example.db.NewsItemDao;
import com.example.httputil.CommonException;
import com.example.httputil.NewsItem;
import com.example.httputil.NewsItemBiz;
import com.example.intelligentkitchenn.R;
import com.example.myactivity.kaiguanshebeiActivity;
import com.example.myapplication.myApplication;
import com.example.myinterface.MysocketInterface;
import com.example.netutil.NetUtil;
import com.example.other.Mysocket;
import com.example.paintcanvas.OnMenuItemClickListener;
import com.example.paintcanvas.roundmenu;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class secondFragment extends android.support.v4.app.Fragment implements
		OnClickListener {
	private roundmenu mCircleMenuLayout;
	private Socket mysocket = null;
	private InputStream in;
	public boolean isConnected = false;
	Thread receiverThread;
	PrintWriter printWriter = null;
	BufferedReader reader;
	public TextView shezhi;
	public ImageView tixing;
	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				String result = msg.getData().get("msg").toString();
				Log.i("来自服务器的消息", result);
			} else if (msg.what == 2) {
				receiverThread = new Thread(new MyReceiverRunnable());
				receiverThread.start();
				Log.i("tcpip", "receive success");
				isConnected = true;
			}
		}
	};
	private String[] mItemTexts = new String[] { "实时数据 ", "开关设备", "定时烹饪",
			"火力调节", "氛围渲染", "危险报警" };
	private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.circle_menu, container, false);
//	   shezhi = (TextView) v.findViewById(R.id.yaokong_shezhi);
		tixing = (ImageView) v.findViewById(R.id.yaokong_tixing);
		mCircleMenuLayout = (roundmenu) v.findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setmenuitem_text_icon(mItemTexts, mItemImgs);
		mCircleMenuLayout
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public void onmenuitem(View view, int pos) {
						// TODO Auto-generated method stub
						Toast.makeText(myApplication.GetContext(),
								mItemTexts[pos], Toast.LENGTH_LONG).show();
						 
						switch (pos) {
						case 0:
							
							break;
						case 1:
							startActivity(new Intent(getActivity(),
									kaiguanshebeiActivity.class));
							break;
						case 2:
							break;
						case 3:
							break;
						case 4:
							break;
						case 5:
							break;
						}
						
					}

					@Override
					public void onmenucentre(View view) {
						// TODO Auto-generated method stub
//						Toast.makeText(myApplication.GetContext(), "centre",
//								Toast.LENGTH_LONG).show();
//						send("123456");
						 if (Mysocket.getState() == 0) {
						 connectServer();
						 Toast.makeText(getActivity(), "wifi已连接!", Toast.LENGTH_SHORT)
						 .show();
						 } else {
						 Toast.makeText(getActivity(), "当前wifi已经连接!", Toast.LENGTH_SHORT)
						 .show();
						 }
					}
				});
		// shezhi.setOnClickListener(this);
		return v;
	}

	public void connectServer() {
		Mysocket.connectServer(myHandler, new MysocketInterface() {

			@Override
			public void onsuccess(Socket socket) {
				mysocket = socket;
				
			}

			@Override
			public void onfailued(Socket socket) {
				
			}
		});

	}

	private class MyReceiverRunnable implements Runnable {

		public void run() {

			while (true) {
				if (isConnected) {
					if (mysocket != null && mysocket.isConnected()) {

						String result = readFromInputStream(Mysocket.getIn());

						try {
							if (!result.equals("")) {
								Message msg = new Message();
								msg.what = 1;
								Bundle data = new Bundle();
								data.putString("msg", result);
								msg.setData(data);
								myHandler.sendMessage(msg);
							}

						} catch (Exception e) {
							// Log.e(tag, "--->>read failure!" + e.toString());
						}
					}
				}
				try {
					Thread.sleep(100L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.yaokong_shezhi:
		// if (Mysocket.getState() == 0) {
		// connectServer();
		// tixing.setText("wifi已连接");
		// } else {
		// Toast.makeText(getActivity(), "当前wifi已经连接!", Toast.LENGTH_SHORT)
		// .show();
		// }
		// break;

		default:
			break;
		}

	}

}
