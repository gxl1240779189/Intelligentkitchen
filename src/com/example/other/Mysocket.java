package com.example.other;

import java.io.InputStream;
import java.net.Socket;

import android.os.Handler;
import android.os.Looper;
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
	public static Socket socket=null;	
	public static int state=0;
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
	public static void connectServer(final Handler handler,final MysocketInterface mysocketinterface) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Looper.prepare();
					socket = new Socket("192.168.1.106",
							Integer.parseInt("8080"));
					in = socket.getInputStream();
					handler.sendEmptyMessage(2);
					state=1;
					System.out.println("���ӷ������ɹ�");
					mysocketinterface.onsuccess(socket);
					Toast.makeText(myApplication.GetContext(), "�ɹ�",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					System.out.println("���ӷ�����ʧ��" + e);
					Toast.makeText(myApplication.GetContext(), "ʧ��",
							Toast.LENGTH_SHORT).show();
					mysocketinterface.onfailued(socket);
					e.printStackTrace();
				}
			}
		}).start();
	}


}
