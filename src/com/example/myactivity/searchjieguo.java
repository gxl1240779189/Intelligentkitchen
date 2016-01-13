package com.example.myactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.intelligentkitchenn.R;
import com.example.myfragment.selectlistview;
import com.example.other.StringtoURL;

public class searchjieguo extends FragmentActivity implements OnClickListener {

	public static android.support.v4.app.FragmentManager fragmentmanager;
	public static android.support.v4.app.FragmentTransaction fragment;
	public View page1view;
	public View page2view;
	public TextView page1textview;
	public TextView page2textview;
	public selectlistview listview_zuixing;
	public selectlistview listview_zuirei;
	public ImageView houtui;
	public String foodname;
	public TextView selecttitle;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_title);
		foodname=getIntent().getExtras().getString("foodname");
		Log.i("foodname", foodname);
		listview_zuixing = new selectlistview("http://so.meishi.cc/?q="+StringtoURL.toUtf8String(foodname)+"&sort=time&page=",1);
		page1view = findViewById(R.id.page1);
		page2view = findViewById(R.id.page2);
		selecttitle=(TextView) findViewById(R.id.select_title);
		selecttitle.setText(foodname);
		houtui=(ImageView) findViewById(R.id.houtui);
		page1textview = (TextView) findViewById(R.id.select_page1);
		page2textview = (TextView) findViewById(R.id.select_page2);
		page1view.setOnClickListener(this);
		page2view.setOnClickListener(this);
		houtui.setOnClickListener(this);
		init();
	}

	void init() {
		fragmentmanager = getSupportFragmentManager();
		fragment = fragmentmanager.beginTransaction();
		fragment.add(R.id.select_content, listview_zuixing);
		fragment.commit();
	}

	void clearview() {
		page1view.setBackgroundResource(R.drawable.btn_gray_big_normal);
		page1textview.setTextColor(Color.parseColor("#82858b"));
		page2view.setBackgroundResource(R.drawable.btn_gray_big_normal);
		page2textview.setTextColor(Color.parseColor("#82858b"));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		clearview();
		FragmentTransaction fragmenttransaction=fragmentmanager.beginTransaction();
		hideFragments(fragmenttransaction);
		switch (v.getId()) {
		case R.id.page1:
			page1view.setBackgroundResource(R.drawable.btn_gray_big_pressed);
			page1textview.setTextColor(Color.parseColor("#ffffff"));
			fragmenttransaction.show(listview_zuixing);
			break;
		case R.id.page2:
			page2view.setBackgroundResource(R.drawable.btn_gray_big_pressed);
			page2textview.setTextColor(Color.parseColor("#ffffff"));
			if (listview_zuirei != null) {
				fragmenttransaction.show(listview_zuirei);
			} else {
				listview_zuirei = new selectlistview("http://so.meishi.cc/?&q="+StringtoURL.toUtf8String(foodname)+"&sort=lastdotime&page=",1);
				fragmenttransaction.add(R.id.select_content, listview_zuirei);
			} 
			break;
			
		case R.id.houtui:
			finish();
			break;
		}
		fragmenttransaction.commit();
	}
	
	private void hideFragments(
			android.support.v4.app.FragmentTransaction fragment) {
		if (listview_zuirei != null) {
			fragment.hide(listview_zuirei);
		}
		if (listview_zuixing != null) {
			fragment.hide(listview_zuixing);
		}
	}
}
