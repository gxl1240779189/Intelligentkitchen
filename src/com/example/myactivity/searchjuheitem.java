package com.example.myactivity;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.juheFoodteachAdapter;
import com.example.httputil.CommonException;
import com.example.httputil.FoodFuliao;
import com.example.httputil.Foodcontent;
import com.example.httputil.JsonBean.Steps;
import com.example.httputil.JsonBean.foodItem;
import com.example.intelligentkitchen.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class searchjuheitem extends Activity implements OnClickListener {
	public foodItem fooditem;
	public ListView teachcontent_listview;
	public ProgressBar mProgressBar;
	public TextView title_textview;
	ImageView showfood;
	TextView showtitle;
	TextView showdetail;
	TextView juhe_zhuliao_neirong;
	TextView juhe_fuliao_neirong;
	TextView headTV;
	View show_xian;
	ImageView btn_back;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private int flag;
	List<Foodcontent> foodcontents;
	List<Steps> steps;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juheteachcontent);
		Intent intent = this.getIntent();
		fooditem = (foodItem) intent.getSerializableExtra("foodItem");
		steps = fooditem.getSteps();
		for (Steps step : steps) {
			Log.i("steps", step.getStep());
		}
		juhe_zhuliao_neirong = (TextView) findViewById(R.id.juhe_zhuliao_neirong);
		juhe_fuliao_neirong = (TextView) findViewById(R.id.juhe_fuliao_neirong);
		teachcontent_listview = (ListView) findViewById(R.id.teachcontent__listview);
		headTV = (TextView) findViewById(R.id.headTV);
		mProgressBar = (ProgressBar) findViewById(R.id.teachcontent__newsContentPro);
		showfood = (ImageView) findViewById(R.id.showfood);
		showtitle = (TextView) findViewById(R.id.showtitle);
		showdetail = (TextView) findViewById(R.id.showdetail);
		title_textview = (TextView) findViewById(R.id.headTV);
		show_xian = findViewById(R.id.show_xian);
		btn_back = (ImageView) findViewById(R.id.backBtn);
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(searchjuheitem.this));
		btn_back.setOnClickListener(this);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.xiaolian)
				.showImageForEmptyUri(R.drawable.xiaolian)
				.showImageOnFail(R.drawable.xiaolian).cacheInMemory()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		imageLoader
				.displayImage(fooditem.getAlbums().get(0), showfood, options);
		headTV.setText(fooditem.getTitle());
		showtitle.setText(fooditem.getTitle());
		showdetail.setText(fooditem.getImtro());
		juhe_fuliao_neirong.setText(fooditem.getBurden());
		juhe_zhuliao_neirong.setText(fooditem.getIngredients());
		teachcontent_listview.setAdapter(new juheFoodteachAdapter(
				searchjuheitem.this, steps, R.layout.foodlistviewteachitem));
		setListViewHeightBasedOnChildren(teachcontent_listview);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backBtn:
			finish();
			break;

		default:
			break;
		}
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += (listItem.getMeasuredHeight() + 100);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ +(listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
