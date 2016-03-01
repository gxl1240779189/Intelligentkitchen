package com.example.myactivity;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.juhelistviewAdapter;
import com.example.httputil.JsonBean;
import com.example.httputil.JsonBean.Steps;
import com.example.httputil.JsonBean.foodItem;
import com.example.intelligentkitchen.R;
import com.example.myfragment.selectlistview;
import com.example.other.StringtoURL;
import com.example.utils.NetUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class searchjuhe extends Activity implements OnItemClickListener {
	public String foodname = null;
	public ListView listview;
	public List<foodItem> data;
	public ImageView houtui;
	public JsonBean jsonBean;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchjuhe_layout);
		houtui = (ImageView) findViewById(R.id.houtui);
		houtui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		jsonBean = (JsonBean) getIntent().getSerializableExtra("searchjieguo");
		listview = (ListView) findViewById(R.id.search_jiegu);
		listview.setOnItemClickListener(this);
		data = new ArrayList<foodItem>();
		com.example.httputil.JsonBean.Result result = jsonBean.getResult();
		data = result.getData();
		listview.setAdapter(new juhelistviewAdapter(searchjuhe.this, data));

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i("item", data.get(position).getTitle());
		Intent intent = new Intent();
		intent.setClass(searchjuhe.this, searchjuheitem.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("foodItem", data.get(position));
		intent.putExtras(bundle);
		this.startActivity(intent);
	}
};
