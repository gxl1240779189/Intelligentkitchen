package com.example.myactivity;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.example.broadcastreceiver.SMS_receiver;
import com.example.httputil.User;
import com.example.intelligentkitchenn.R;
import com.example.myinterface.duanxinglistener;
import com.example.other.fuwuqiurl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	public ImageView Register_back;
	public EditText register_phone;
	public EditText register_check_upass;
	public EditText register_upass;
	public Button register_btn;
	public Button register_get_check_pass;
	public EventHandler eh ;
	public SMS_receiver broadreceiver;


	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_register_act);
		SMSSDK.initSDK(this, "c7ab0de860bc", "2f031be4660cf6d57be19df4ab57cf1f");
		Register_back = (ImageView) findViewById(R.id.register_back);
		register_phone = (EditText) findViewById(R.id.register_phone);
		register_check_upass = (EditText) findViewById(R.id.register_check_upass);
		register_upass = (EditText) findViewById(R.id.register_upass);
		register_btn = (Button) findViewById(R.id.register_btn);
		register_get_check_pass = (Button) findViewById(R.id.register_get_check_pass);
		broadreceiver=new SMS_receiver();
		registerReceiver(broadreceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
		broadreceiver.setlistener(new duanxinglistener() {
			
			@Override
			public void get_haoma(String msg) {
				register_check_upass.setText(msg);
			}
		});
		Register_back.setOnClickListener(this);
		register_btn.setOnClickListener(this);
		register_get_check_pass.setOnClickListener(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_back:
			finish();
			break;

		case R.id.register_get_check_pass:
			SendsmsRandom();
			break;
			
		case R.id.register_btn:
			confirmsms();
			break;
		}
	}

	public void SendsmsRandom() {	
		eh = new EventHandler() {

			@Override
			public void afterEvent(int event, int result, Object data) {

				if (result == SMSSDK.RESULT_COMPLETE) {
					// 回调完成
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						System.out.println("提交验证码成功");
						new HttpUtils().send(HttpMethod.GET,
								fuwuqiurl.denglu + "?&username=" + register_phone.getText().toString() + "&password="
										+ register_upass.getText().toString(), new RequestCallBack<String>() {

									@Override
									public void onFailure(HttpException arg0, String arg1) {					
										Toast.makeText(RegisterActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
									}

									@Override
									public void onSuccess(ResponseInfo<String> arg0) {
								         Gson gson=new GsonBuilder().create();
								         User object=gson.fromJson(arg0.result,User.class);								       
								         Toast.makeText(RegisterActivity.this, object.getUsername(), Toast.LENGTH_SHORT).show();
									}
								});
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						System.out.println("获取验证码成功");
					} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
						// 返回支持发送验证码的国家列表
					}
				} else {
					((Throwable) data).printStackTrace();
				}
			}
		};
		SMSSDK.registerEventHandler(eh); // 注册短信回调
		SMSSDK.getVerificationCode("86", register_phone.getText().toString());
	}
	
	public void confirmsms()
	{
		SMSSDK.submitVerificationCode("86", register_phone.getText().toString(), register_check_upass.getText().toString());
		
	}
	
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SMSSDK.unregisterEventHandler(eh);
	}
}
