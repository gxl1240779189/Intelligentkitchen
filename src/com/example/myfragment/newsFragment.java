package com.example.myfragment;

/*
 * 错误分析，为什么第一次进来的时候都是启动了两个fragment，并且每一次运行都不是构造当前的fragment？？？？
问题已解决，Xlistview的数据和视图更新放到同一个函数中去；

 * 
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

import com.example.adapter.NewsItemAdapter;
import com.example.db.NewsItemDao;
import com.example.httputil.CommonException;
import com.example.httputil.Constaint;
import com.example.httputil.NewsItem;
import com.example.httputil.NewsItemBiz;
import com.example.intelligentkitchenn.R;
import com.example.myactivity.Newcontent;
import com.example.netutil.NetUtil;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class newsFragment extends android.support.v4.app.Fragment implements
		IXListViewRefreshListener, IXListViewLoadMore {

	private XListView mListView;
	// 创建XListView对象

	private int newsType;
	/**
	 * 当前页面
	 */
	private int currentPage = 1;
	/**
	 * 处理新闻的业务类
	 */
	private NewsItemBiz mNewsItemBiz;
	private NewsItemBiz mNewsItemBiz1;
	/**
	 * 扩展的ListView
	 */
	private XListView mXListView;
	/**
	 * 数据适配器
	 */
	private NewsItemAdapter mAdapter;

	/**
	 * 数据
	 */
	private List<NewsItem> mDatas = new ArrayList<NewsItem>();
	private NewsItemDao newitemdao;
	private int Loadmore = 2;
	private final int refresh = 1;
	private boolean isLoadingDataFromNetWork = false;
	private boolean firstin = true;
	

	public newsFragment(int newType) {
		// TODO Auto-generated constructor stub
		this.newsType = newType;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.news_fragment, container, false);
		mAdapter = new NewsItemAdapter(getActivity(), mDatas);
		mNewsItemBiz = new NewsItemBiz();
		mNewsItemBiz1 = new NewsItemBiz();
		/**
		 * 初始化
		 */
		mXListView = (XListView) v.findViewById(R.id.xListView);
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
				// TODO Auto-generated method stub
				List<NewsItem> mDatas = mAdapter.returnmDatas();
				NewsItem newitem = mDatas.get(position - 1);
				Intent intent = new Intent(getActivity(), Newcontent.class);
				intent.putExtra("url", newitem.getLink());
				startActivity(intent);
			}
		});

		return v;
	}

	@Override
	public void onRefresh() {

		new LoadDatasTask().execute();
	}

	class LoadDatasTask extends AsyncTask<Void, Void, Void> {
		private  boolean dbhasdata=false;	
		private List<NewsItem> newsItems1 = new ArrayList<NewsItem>();
		protected void onPostExecute(Void result) {
			if(newsItems1.size()!=0)
			{
			  mAdapter.setDatas(newsItems1);
			  mAdapter.notifyDataSetChanged();
			}
			mXListView.stopRefresh();
			mXListView.stopLoadMore();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				if (NetUtil.checkNet(getActivity())) {
					List<NewsItem> newsItems = new ArrayList<NewsItem>();
					newsItems = mNewsItemBiz.getNewsItems(newsType, 1);
					Log.i("kanyikan", newsType+"");
					newitemdao = new NewsItemDao(getActivity());
					newitemdao.deleteAll(newsType);
					newitemdao.add(newsItems);
					mAdapter.setDatas(newsItems);
					currentPage = 1;
				} else {
					newitemdao = new NewsItemDao(getActivity());
					newsItems1 = newitemdao.list(newsType, 1);
					for (NewsItem newsItem : newsItems1) {
						Log.i("news", newsItem.toString());
					}
//					if(newsItems1.size()==0)
//					{
//						Log.i("warning", "当前本地数据库中没数据");
//					}else
//					{
//				     dbhasdata=true;
//					
//					}

				}
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
			newitemdao = new NewsItemDao(getActivity());
			// 当前数据是从网络获取的
			if (NetUtil.checkNet(getActivity())) {
				List<NewsItem> newsItems = new ArrayList<NewsItem>();
				currentPage += 1;
				Log.i("currentPage", String.valueOf(currentPage));
				try {
					newsItems = mNewsItemBiz1.getNewsItems(newsType,
							currentPage);
					if (newsItems.size() == 0) {
						Log.i("123", "123");
					} else {
						Log.i("456", "456");
						Log.i("456", newsItems.get(0).getTitle());
					}
					newitemdao.add(newsItems);
					mAdapter.addAll(newsItems);

				} catch (CommonException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
			{
				currentPage += 1;
				List<NewsItem> newsItems = new ArrayList<NewsItem>();
				newsItems = newitemdao.list(newsType, currentPage);
				mDatas = newsItems;
			}
			return null;
		}

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new loadmore().execute();
	}

}
