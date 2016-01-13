package com.example.myactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;



import com.example.intelligentkitchenn.R;
import com.example.myinterface.MysocketInterface;
import com.example.other.Mysocket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class YaokongActivity extends Activity implements OnClickListener {
public Button yaokong;
private Socket mysocket=null;
private InputStream in;
public boolean isConnected = false;
Thread receiverThread;
PrintWriter printWriter = null;
BufferedReader reader;
public TextView shezhi;
public Handler myHandler = new Handler() {
	public void handleMessage(Message msg) {
		if (msg.what == 1) {
			String result = msg.getData().get("msg").toString();
			Log.i("来自服务器的消息", "result");
		} else if (msg.what == 2) {
			receiverThread = new Thread(new MyReceiverRunnable());
			receiverThread.start();
			Log.i("tcpip", "receive success");
			isConnected = true;
		}
	}
};
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.yaokong);
	yaokong=(Button) findViewById(R.id.yaokong_button);
	yaokong.setOnClickListener(this);
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

public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.yaokong_button:
		if(Mysocket.getState()==1)
		{
		mysocket=Mysocket.getSocket();
		send("22222");
		}else
		{
			Toast.makeText(YaokongActivity.this, "当前wifi尚未连接，请到设置页面中连接!", Toast.LENGTH_SHORT).show();
		}
		break;

	default:
		break;
	}
}
}
