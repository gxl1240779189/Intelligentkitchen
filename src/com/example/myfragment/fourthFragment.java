package com.example.myfragment;

import com.example.intelligentkitchen.R;
import com.example.myactivity.dengluActivity;
import com.example.myactivity.loveFood;
import com.example.other.Const;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class fourthFragment extends android.support.v4.app.Fragment implements
		OnClickListener {
	private TextView denglu;
	private ImageView xiugaiziliao;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private ImageView touxiang;
	private Boolean shifoudenglu = false;
	private String username;
	private String userid;
	private TextView shoucang;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.my_index, container, false);
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		shoucang=(TextView) v.findViewById(R.id.shoucang);
		shoucang.setOnClickListener(this);
		Log.i("123", "123");
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_empty_dish)
				.showImageForEmptyUri(R.drawable.ic_empty_dish)
				.showImageOnFail(R.drawable.ic_empty_dish).cacheInMemory()
				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(300)).build();
		touxiang = (ImageView) v.findViewById(R.id.my_index_login_image);
		denglu = (TextView) v.findViewById(R.id.my_index_login_text);
		xiugaiziliao = (ImageView) v.findViewById(R.id.xiugaiziliao);
		denglu.setOnClickListener(this);
		xiugaiziliao.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.my_index_login_text:
			startActivityForResult(new Intent(getActivity(),
					dengluActivity.class), 1);
			break;

		case R.id.xiugaiziliao:
			if (shifoudenglu) {
				Intent intent = new Intent(getActivity(),
						com.example.myactivity.xiugaiziliao.class);
				intent.putExtra("username", username);
				intent.putExtra("userid", userid);
				startActivityForResult(intent, 2);
			} else {
				Toast.makeText(getActivity(), "修改资料,请先登录!", Toast.LENGTH_SHORT)
						.show();
				startActivityForResult(new Intent(getActivity(),
						dengluActivity.class), 1);
			}
			break;
			
		case R.id.shoucang:
			startActivity(new Intent(getActivity(),loveFood.class));
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == 1) {
				username = data.getStringExtra("username");
				userid = data.getStringExtra("userid");
				denglu.setText(username);
				shifoudenglu = true;
				imageLoader.clearMemoryCache();
				imageLoader.clearDiscCache();
				imageLoader.displayImage(Const.DOWNLOAD_URL + userid
						+ "/touxiang.jpg", touxiang, options);
			}

			break;

		case 2:
			if (resultCode == 2) {
				imageLoader.clearMemoryCache();
				imageLoader.clearDiscCache();
				imageLoader.displayImage(Const.DOWNLOAD_URL + userid
						+ "/touxiang.jpg", touxiang, options);
			}
			break;
		}
	}
}
