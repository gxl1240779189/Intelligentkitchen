package com.example.other;

import java.io.InputStream;
import java.net.Socket;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.myApplication;
import com.example.myinterface.MysocketInterface;

public class Mysocket {

	public static int getState() {
		return state;
	}

	public static void setState(int state) {
		Mysocket.state = state;
	}

	public static Socket socket = null;
	public static int state = 0;

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		Mysocket.socket = socket;
	}

	public static InputStream getIn() {
		return in;
	}

	public static void setIn(InputStream in) {
		Mysocket.in = in;
	}

	public static InputStream in;

	public static void connectServer(final String IP, final String Port) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Log.i("gxl", IP + Port);
					Looper.prepare();
					socket = new Socket(IP, Integer.parseInt(Port));
					state = 1;
					in = socket.getInputStream();
					Toast.makeText(myApplication.GetContext(), "连接服务器成功!",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(myApplication.GetContext(), "连接服务器失败!",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		}).start();
	}

}
