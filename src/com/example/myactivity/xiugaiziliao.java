package com.example.myactivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.intelligentkitchen.R;
import com.example.other.Const;
import com.example.other.ServerUtils;
import com.example.other.StringtoURL;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class xiugaiziliao extends Activity implements OnClickListener {
	private ImageView houtui;
	private View xiugai_touxiang;
	private ImageView touxiang;
	ProgressDialog dialog = null;
	String result = "";
	String userid;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	String path = null;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xiugaiziliao);
		houtui = (ImageView) findViewById(R.id.xiugaiziliao_houtui);
		xiugai_touxiang = findViewById(R.id.xiugai_touxiang);
		touxiang = (ImageView) findViewById(R.id.touxiang);
		houtui.setOnClickListener(this);
		xiugai_touxiang.setOnClickListener(this);
		imageLoader.init(ImageLoaderConfiguration
				.createDefault((xiugaiziliao.this)));
		userid = getIntent().getStringExtra("userid");
		Log.i("123", "123");
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.xiaolian)
				.showImageForEmptyUri(R.drawable.xiaolian)
				.showImageOnFail(R.drawable.xiaolian).cacheInMemory()
				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(300)).build();
		imageLoader.clearMemoryCache();
		imageLoader.clearDiscCache();
		imageLoader.displayImage(Const.DOWNLOAD_URL + userid + "/touxiang.jpg",
				touxiang, options);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.xiugaiziliao_houtui:
			Intent intent1 = new Intent();
			setResult(2, intent1);
			finish();
			break;

		case R.id.xiugai_touxiang:
			Intent intent = new Intent();
			/* 开启Pictures画面Type设定为image */
			intent.setType("image/*");
			/* 使用Intent.ACTION_GET_CONTENT这个Action */
			intent.setAction(Intent.ACTION_GET_CONTENT);
			/* 取得相片后返回本画面 */
			startActivityForResult(intent, 1);
			break;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		Uri uri = null;
		switch (requestCode) {
		case 1:// 相册
			if (data == null) {
				return;
			}
			uri = data.getData();
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(uri, proj, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			path = cursor.getString(column_index);// 图片在的路径
			new xiugaitupianTask().execute();
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	class xiugaitupianTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("username", getIntent().getStringExtra("username"));
			result = ServerUtils.formUpload(Const.UPLOAD_URL + "?&username="
					+ userid, path);
			Log.e("jj", "result:" + result);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			imageLoader.clearMemoryCache();
			imageLoader.clearDiscCache();
			imageLoader.displayImage(Const.DOWNLOAD_URL + userid
					+ "/touxiang.jpg", touxiang, options);
			super.onPostExecute(result);
		}

	}

}
