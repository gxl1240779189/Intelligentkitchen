package com.example.myactivity;

import java.util.List;

import com.example.adapter.FoodsItemAdapter;
import com.example.db.FoodsItemDao;
import com.example.httputil.FoodsItem;
import com.example.intelligentkitchen.R;
import com.example.ui.mylistview;
import com.example.ui.mylistview.OnItemDeleteListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class loveFood extends Activity {
	public mylistview listview;
	public List<FoodsItem> data;
	public ImageView houtui;
	public FoodsItemAdapter mAdapter;
	public TextView xianshi;
	public FoodsItemDao foodsitemdao;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.love_food);
		foodsitemdao=new FoodsItemDao(this);
		data=foodsitemdao.list();
		xianshi=(TextView) findViewById(R.id.xianshi);
		listview=(mylistview) findViewById(R.id.shoucang_listview);
	
		if(data.size()!=0)
		{
			xianshi.setVisibility(View.GONE);
			mAdapter=new FoodsItemAdapter(loveFood.this, data);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					List<FoodsItem> mDatas = mAdapter.returnmDatas();
					Log.i("position", position+"");
					FoodsItem fooditem = mDatas.get(position);
					Bundle bundle = new Bundle();
					bundle.putSerializable("fooditem", fooditem);
					Intent intent = new Intent(loveFood.this, teachcontent.class);
					intent.putExtras(bundle);
					startActivity(intent);	
				}
			});
		}
		else
		{
			listview.setVisibility(View.GONE);
		}	
		houtui=(ImageView) findViewById(R.id.houtui);
		houtui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listview.setAdapter(mAdapter);
		listview.setOnItemDeleteListener(new OnItemDeleteListener() {
			
			@Override
			public void onItemDelete(int selectedItem) {
				// TODO Auto-generated method stub
				Log.i("selectedItem", selectedItem+"");
				foodsitemdao.delete(data.get(selectedItem));
				data.remove(selectedItem);
				for (FoodsItem iterable_element : data) {
					Log.i("gxl", iterable_element.toString());
				}
			
				mAdapter.notifyDataSetChanged();
			}
		});
	}
}
