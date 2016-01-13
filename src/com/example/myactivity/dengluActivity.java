package com.example.myactivity;

import java.util.HashMap;

import javax.security.auth.callback.Callback;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;



import com.example.httputil.ResponseObject;
import com.example.httputil.User;
import com.example.intelligentkitchenn.R;
import com.example.other.LoadDialog;
import com.example.other.fuwuqiurl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.OtherUtils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class dengluActivity extends Activity implements OnClickListener{

	public String login_name;
	public String login_password;
	public EditText login_name_edittext;
	public EditText login_password_edittext;
	public Button login_button;
	public TextView register_textview;
	public ImageView  login_back_imageview;
	public TextView login_byqq_textview;
	public Dialog dialog=null;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_login_act);
		ShareSDK.initSDK(this);
		login_name_edittext = (EditText) findViewById(R.id.login_uname);
		login_password_edittext = (EditText) findViewById(R.id.login_pass);
		login_button = (Button) findViewById(R.id.login_btn);
		register_textview=(TextView) findViewById(R.id.login_register);
		login_back_imageview=(ImageView) findViewById(R.id.login_back);
		login_byqq_textview=(TextView) findViewById(R.id.login_by_qq);
		dialog=LoadDialog.createLoadingDialog(dengluActivity.this, "QQ登录中");
		register_textview.setOnClickListener(this);
		login_button.setOnClickListener(this);
		login_back_imageview.setOnClickListener(this);
		login_byqq_textview.setOnClickListener(this);
	
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_btn:
			login_name = login_name_edittext.getText().toString();
			login_password = login_password_edittext.getText().toString();
			new HttpUtils().send(HttpMethod.GET,
					fuwuqiurl.denglu + "?&username=" + login_name + "&password="
							+ login_password, new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {					
							Toast.makeText(dengluActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
					         Gson gson=new GsonBuilder().create();
					         User object=gson.fromJson(arg0.result,User.class);
					         Log.i("username", object.getUsername());
						}
					});
			break;

		case R.id.login_register:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
			
		case R.id.login_back:
			finish();
			break;
			
		case R.id.login_by_qq:		
			Platform platQQ = ShareSDK.getPlatform(dengluActivity.this, QQ.NAME);
			platQQ.setPlatformActionListener(qqlistener);
			if (platQQ.isValid()) {
//				String uname = platQQ.getDb().getUserName();//获取三方的显示名称
//				System.out.println("验证通过。。。。。。"+uname);
				platQQ.removeAccount();
				platQQ.showUser(null);
				//返回我的页面
				dialog.show();
			}else{
				//如果没有授权登录
				dialog.show();
				platQQ.showUser(null);
				
			}
			break;
		}
	}
	
	 PlatformActionListener qqlistener = new PlatformActionListener() {

		  @Override
		  public void onError(Platform arg0, int arg1, Throwable arg2) {
		   // TODO Auto-generated method stub
		   System.out.println("onError..........");
		  }

		  @Override
		  public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
		   // TODO Auto-generated method stub
		   System.out.println("onComplete........");
		   String uname = arg0.getDb().getUserName();//获取三方的显示名称
		   String number=arg0.getDb().getUserId();
		   Log.i("UserId", number);
		   String icon=arg0.getDb().getUserIcon();
		   String Secret=arg0.getDb().getTokenSecret();
		   System.out.println("验证通过。。。。。。"+uname+" "+icon+" "+Secret);
		   Intent intent=new Intent();
		   intent.putExtra("username", uname);
		   intent.putExtra("userid", number);
		   setResult(1, intent);
           finish();
		  }

		  @Override
		  public void onCancel(Platform arg0, int arg1) {
		   // TODO Auto-generated method stub

		  }
		 };

}
