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
import com.example.myactivity.YaokongActivity;
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
				Log.i("���Է���������Ϣ", "result");
			} else if (msg.what == 2) {
				receiverThread = new Thread(new MyReceiverRunnable());
				receiverThread.start();
				Log.i("tcpip", "receive success");
				isConnected = true;
			}
		}
	};
	private String[] mItemTexts = new String[] { "ʵʱ���� ", "�����豸", "��ʱ���",
			"��������", "��Χ��Ⱦ", "Σ�ձ���" };
	private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.circle_menu, container, false);
//		shezhi = (TextView) v.findViewById(R.id.yaokong_shezhi);
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
						// connectServer();
						startActivity(new Intent(getActivity(),
								YaokongActivity.class));
					}

					@Override
					public void onmenucentre(View view) {
						// TODO Auto-generated method stub
						Toast.makeText(myApplication.GetContext(), "centre",
								Toast.LENGTH_LONG).show();
						send("123456");
					}
				});
//		shezhi.setOnClickListener(this);
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

						String result = readFromInputStream(in);

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
	 * ������Ϣ�ķ���
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
//		case R.id.yaokong_shezhi:
//			if (Mysocket.getState() == 0) {
//				connectServer();
//				tixing.setText("wifi������");
//			} else {
//				Toast.makeText(getActivity(), "��ǰwifi�Ѿ�����!", Toast.LENGTH_SHORT)
//						.show();
//			}
//			break;

		default:
			break;
		}

	}

}