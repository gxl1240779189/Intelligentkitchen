package com.example.myactivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

import com.example.adapter.FoodsItemAdapter;
import com.example.adapter.juhelistviewAdapter;
import com.example.adapter.searchHistoryAdapter;
import com.example.db.mySharedpreferencesHelper;
import com.example.httputil.CommonException;
import com.example.httputil.FoodsItem;
import com.example.httputil.FoodsItemBiz;
import com.example.httputil.JsonBean;
import com.example.httputil.JsonBean.Steps;
import com.example.httputil.JsonBean.foodItem;
import com.example.intelligentkitchen.R;
import com.example.other.StringtoURL;
import com.example.utils.NetUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class search extends Activity implements OnClickListener {
	public ImageView btn_back;
	public EditText search_text;
	public ImageView search_button;
	private FoodsItemBiz mFoodsItemBiz;
	public List<FoodsItem> foodlist = new ArrayList<FoodsItem>();
	private FoodsItemAdapter mAdapter;
	public JsonBean jsonBean;
	public String foodname;
	public ListView search_history_lv;
	public Button clear_history_btn;
	mySharedpreferencesHelper searchHistroty;
	ArrayAdapter<String> arr_adapter;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchlayout);
		search_history_lv = (ListView) findViewById(R.id.search_history_lv);
		clear_history_btn = (Button) findViewById(R.id.clear_history_btn);
		searchHistroty = new mySharedpreferencesHelper(search.this);
		List<String> history_arr = searchHistroty.returnhistroydata();
		if (history_arr.size() > 0) {
			arr_adapter = new searchHistoryAdapter(this, R.layout.searchlistview_item, history_arr);
			search_history_lv.setAdapter(arr_adapter);
		} else {
			search_history_lv.setVisibility(View.GONE);
			clear_history_btn.setVisibility(View.GONE);
		}
		clear_history_btn.setOnClickListener(this);
		btn_back = (ImageView) findViewById(R.id.search_back);
		search_text = (EditText) findViewById(R.id.search_text);
		search_button = (ImageView) findViewById(R.id.search_button);

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
			foodname = search_text.getText().toString();
			new searchFoodTask().execute(foodname);
			break;

		case R.id.clear_history_btn:
			searchHistroty.cleanHistory();
			arr_adapter.clear();
			arr_adapter.notifyDataSetChanged();
			search_history_lv.setVisibility(View.GONE);
		}
	}

	class searchFoodTask extends AsyncTask<String, Void, Void> {
		String response;

		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			parseJSONWithGSON(response);
			if (panduan_shifouhuodeshuju(jsonBean.getError_code())) {
				searchHistroty.save(foodname);
				Intent intent = new Intent(search.this, searchjuhe.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("searchjieguo", jsonBean);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			response = NetUtil
					.sendRequestWithhttpClient("http://apis.juhe.cn/cook/query.php?menu="
							+ StringtoURL.toUtf8String(foodname)
							+ "&dtype=&pn=&rn=&albums=&=&key=62b66f73397452f0bf8354eb599fd43c");
			Log.i("gxl", response);
			return null;
		}
	}

	public void parseJSONWithGSON(String response) {
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<JsonBean>() {
		}.getType();
		jsonBean = gson.fromJson(response, type);
		Log.i("gxl", jsonBean.getResultcode());
		com.example.httputil.JsonBean.Result result = jsonBean.getResult();
	}

	public Boolean panduan_shifouhuodeshuju(int error_code) {
		switch (error_code) {
		case 0:
			return true;
		case 204601:
			Toast.makeText(search.this, "菜谱名称不能为空", Toast.LENGTH_LONG).show();
			return false;
		case 204602:
			Toast.makeText(search.this, "查询不到相关信息", Toast.LENGTH_LONG).show();
			return false;
		case 204603:
			Toast.makeText(search.this, "菜谱名过长", Toast.LENGTH_LONG).show();
			return false;
		case 204604:
			Toast.makeText(search.this, "错误的标签ID", Toast.LENGTH_LONG).show();
			return false;
		case 204605:
			Toast.makeText(search.this, "查询不到数据", Toast.LENGTH_LONG).show();
			return false;
		case 204606:
			Toast.makeText(search.this, "错误的菜谱ID", Toast.LENGTH_LONG).show();
			return false;
		}
		return false;
	}
}
