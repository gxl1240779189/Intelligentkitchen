package com.example.myfragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

import com.example.adapter.FoodsItemAdapter;
import com.example.httputil.CommonException;
import com.example.httputil.FoodsItem;
import com.example.httputil.FoodsItemBiz;
import com.example.httputil.SliderShowViewItem;
import com.example.intelligentkitchen.R;
import com.example.myactivity.GridviewItem;
import com.example.myactivity.loveFood;
import com.example.myactivity.teachcontent;
import com.example.utils.NetUtil;
import com.example.slideshowdemo.customview.SlideShowView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class foodFragment extends Fragment implements
		IXListViewRefreshListener, IXListViewLoadMore {
	public List<FoodsItem> foodlist = new ArrayList<FoodsItem>();
	private XListView mXListView;
	private FoodsItemAdapter mAdapter;
	private FoodsItemBiz mFoodsItemBiz;
	private Boolean firstin = true;
	private GridView gridview;
	private int[] icon = { R.drawable.recipe_lable_1_1,
			R.drawable.recipe_lable_1_2, R.drawable.recipe_lable_2_1,
			R.drawable.recipe_lable_2_3, R.drawable.recipe_lable_4_1,
			R.drawable.recipe_lable_5_2 };
	private String[] iconName = { "家常菜谱", "中华菜系", "各地小吃", "外国菜谱", "烘焙", "我的收藏" };
	private List<Map<String, Object>> data_list;
	private SimpleAdapter sim_adapter;
	private EditText search;
	private FrameLayout slideshowview_framelayout;
	private SlideShowView slideshowview;
	private List<ImageView> imageViewsList;
	private LinearLayout foodfragmenthead;
	public List<SliderShowViewItem> list = new ArrayList<SliderShowViewItem>();
	private int currentPage = 1;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.news_fragment, container, false);
		foodfragmenthead = (LinearLayout) inflater.inflate(
				R.layout.foodfragment_head, null);
		mAdapter = new FoodsItemAdapter(getActivity(), foodlist);
		mFoodsItemBiz = new FoodsItemBiz();
		slideshowview = new SlideShowView(getActivity());
		mXListView = (XListView) v.findViewById(R.id.xListView);
		mXListView.addHeaderView(foodfragmenthead);
		gridview = (GridView) foodfragmenthead.findViewById(R.id.gridview);
		data_list = new ArrayList<Map<String, Object>>();
		// 获取数据
		getData();
		// 新建适配器
		String[] from = { "image", "text" };
		int[] to = { R.id.image, R.id.text };
		sim_adapter = new SimpleAdapter(getActivity(), data_list,
				R.layout.gridview_item, from, to);
		// 配置适配器
		gridview.setAdapter(sim_adapter);
		mXListView.setAdapter(mAdapter);
		mXListView.setPullRefreshEnable(this);
		mXListView.setPullLoadEnable(this);
		if (firstin) {
			mXListView.startRefresh();
			firstin = false;
		} else {
			mXListView.NotRefreshAtBegin();
		}
		mXListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				List<FoodsItem> mDatas = mAdapter.returnmDatas();
				FoodsItem fooditem = mDatas.get(position - 2);
				Bundle bundle = new Bundle();
				bundle.putSerializable("fooditem", fooditem);
				Intent intent = new Intent(getActivity(), teachcontent.class);
				// intent.putExtra("url", fooditem.getLink());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 5) {
					Intent intent=new Intent(getActivity(),
							loveFood.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(getActivity(),
							GridviewItem.class);
					Bundle bundle = new Bundle();
					bundle.putInt("position", position);
					Log.i("position", String.valueOf(position));
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
		return v;
	}

	@Override
	public void onLoadMore() {
		new loadmore().execute();
	}

	public void getData() {
		// cion和iconName的长度是相同的，这里任选其一都可以
		for (int i = 0; i < icon.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", icon[i]);
			map.put("text", iconName[i]);
			data_list.add(map);
		}

	}

	@Override
	public void onRefresh() {
		new LoadDatasTask().execute();
	}

	class LoadDatasTask extends AsyncTask<Void, Void, Void> {
		private boolean dbhasdata = false;
		private List<FoodsItem> foodsItems1 = new ArrayList<FoodsItem>();

		protected void onPostExecute(Void result) {
			if (foodsItems1.size() != 0) {
				mAdapter.setDatas(foodsItems1);
				mAdapter.notifyDataSetChanged();
			}
			// setListViewHeightBasedOnChildren(mXListView);
			mXListView.stopRefresh();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				foodsItems1 = mFoodsItemBiz
						.getNewsItems(
								"http://www.meishij.net/list.php?sortby=update&lm=13&page=1",
								0);
			} catch (CommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	class loadmore extends AsyncTask<Void, Void, Void> {

		protected void onPostExecute(Void result) {
			mXListView.setVisibility(View.GONE);
			mAdapter.notifyDataSetChanged();
			mXListView.setVisibility(View.VISIBLE);
			mXListView.stopRefresh();
			mXListView.stopLoadMore();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			// 当前数据是从网络获取的
			if (NetUtil.checkNet(getActivity())) {
				List<FoodsItem> foodlist = new ArrayList<FoodsItem>();
				currentPage += 1;
				Log.i("currentPage", String.valueOf(currentPage));
				try {
					foodlist = mFoodsItemBiz.getNewsItems(
							"http://www.meishij.net/list.php?sortby=update&lm=13&page="
									+ String.valueOf(currentPage), 0);
					mAdapter.addAll(foodlist);

				} catch (CommonException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

	}

	/**
	 * 动态设置ListView的高度
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(XListView listView) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		Log.i("listAdapter.getCount()", listAdapter.getCount() + "");
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
