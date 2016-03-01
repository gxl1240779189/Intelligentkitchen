package com.example.myactivity;

import java.net.Socket;

import com.example.intelligentkitchen.R;
import com.example.myapplication.myApplication;
import com.example.myinterface.MysocketInterface;
import com.example.other.Mysocket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class shezhiWifi extends Activity implements OnClickListener {

	public EditText IP;
	public EditText Port;
	public String IP_text;
	public String Port_text;
	public Button connectserver;
	public TextView zhuangtai;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shezhiwifi);
		IP = (EditText) findViewById(R.id.IP);
		Port = (EditText) findViewById(R.id.port);
		zhuangtai = (TextView) findViewById(R.id.zhuangtai);
		if (Mysocket.getState() == 1)
			zhuangtai.setText("已连接");
		connectserver = (Button) findViewById(R.id.connect_server);
		connectserver.setOnClickListener(this);

	}

	public void connectServer(String ip, String port) {
		Mysocket.connectServer(ip, port);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.connect_server:
			Log.i("gxl", "gxl");
			IP_text = IP.getText().toString();
			Port_text = Port.getText().toString();
			if (Mysocket.getState() == 0) {
				connectServer(IP_text, Port_text);
			} else {
				Toast.makeText(shezhiWifi.this, "当前wifi已经连接!",
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}
}
