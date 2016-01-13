package com.example.myactivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

import com.example.adapter.FoodsItemAdapter;
import com.example.httputil.CommonException;
import com.example.httputil.FoodsItem;
import com.example.httputil.FoodsItemBiz;
import com.example.intelligentkitchenn.R;
import com.example.other.StringtoURL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

public class search extends Activity implements OnClickListener,IXListViewRefreshListener, IXListViewLoadMore{
	public ImageView btn_back;
	public EditText search_text;
	public ImageView search_button;
	private FoodsItemBiz mFoodsItemBiz;
	private XListView mXListView;
	public List<FoodsItem> foodlist = new ArrayList<FoodsItem>();
	private FoodsItemAdapter mAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchlayout);
		btn_back=(ImageView) findViewById(R.id.search_back);
		search_text=(EditText) findViewById(R.id.search_text);
		search_button=(ImageView) findViewById(R.id.search_button);	
		mXListView=(XListView) findViewById(R.id.search_xListView);
		mFoodsItemBiz = new FoodsItemBiz();
		mAdapter = new FoodsItemAdapter(this, foodlist);
		search_button.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search_back:
			finish();
			break;

		case R.id.search_button:
			Intent intent=new Intent(this, searchjieguo.class);
			Bundle bundle=new Bundle();
			bundle.putString("foodname",search_text.getText().toString());
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
}
