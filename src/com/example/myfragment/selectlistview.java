package com.example.myfragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

import com.example.adapter.FoodsItemAdapter;
import com.example.db.NewsItemDao;
import com.example.httputil.CommonException;
import com.example.httputil.FoodsItem;
import com.example.httputil.FoodsItemBiz;
import com.example.httputil.NewsItem;
import com.example.intelligentkitchenn.R;
import com.example.myactivity.teachcontent;
import com.example.myfragment.foodFragment.LoadDatasTask;
import com.example.netutil.NetUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class selectlistview extends Fragment implements
		IXListViewRefreshListener, IXListViewLoadMore {
	public String link;
	public List<FoodsItem> foodlist = new ArrayList<FoodsItem>();
	private XListView mXListView;
	private FoodsItemAdapter mAdapter;
	private FoodsItemBiz mFoodsItemBiz;
	private boolean firstin = true;
	private int currentPage = 1;
	private int flag;
	private int leixing=0;
	

	@SuppressLint("ValidFragment")
	public selectlistview(String link,int leixing) {
		super();
		this.link = link;
		this.leixing=leixing;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.selectlistview, container, false);
		mAdapter = new FoodsItemAdapter(getActivity(), foodlist);
		mFoodsItemBiz = new FoodsItemBiz();
		mXListView = (XListView) view.findViewById(R.id.select_listview);
		mFoodsItemBiz = new FoodsItemBiz();
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
				FoodsItem newitem = mDatas.get(position-1);
				Intent intent = new Intent(getActivity(), teachcontent.class);
				intent.putExtra("url", newitem.getLink());
				Bundle bundle=new Bundle();
				bundle.putInt("flag", flag);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		return view;
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new loadmore().execute();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
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
			mXListView.stopRefresh();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				foodsItems1 = mFoodsItemBiz
						.getNewsItems(link+"1",leixing);
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
					foodlist = mFoodsItemBiz
							.getNewsItems(link
									+ String.valueOf(currentPage),0);
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

}
