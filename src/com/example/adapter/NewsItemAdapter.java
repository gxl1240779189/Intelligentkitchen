package com.example.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.httputil.NewsItem;
import com.example.intelligentkitchenn.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsItemAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<NewsItem> mDatas;

	/**
	 * ʹ����github��Դ��ImageLoad��������ݼ���
	 */
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public NewsItemAdapter(Context context, List<NewsItem> datas) {
		this.mDatas = datas;
		mInflater = LayoutInflater.from(context);

		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory()
				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}

	public List<NewsItem> returnmDatas() {
		return this.mDatas;
	}

	public void addAll(List<NewsItem> mDatas) {
		this.mDatas.addAll(mDatas);
	}

	public void setDatas(List<NewsItem> mDatas) {
		this.mDatas.clear();
		this.mDatas.addAll(mDatas);
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.xlistviewitem, null);
			holder = new ViewHolder();

			holder.mContent = (TextView) convertView
					.findViewById(R.id.id_content);
			holder.mTitle = (TextView) convertView.findViewById(R.id.id_title);
			holder.mDate = (TextView) convertView.findViewById(R.id.id_date);
			holder.mImg = (ImageView) convertView.findViewById(R.id.id_newsImg);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		NewsItem newsItem = mDatas.get(position);
		holder.mTitle.setText(newsItem.getTitle());
		holder.mContent.setText(newsItem.getContent());
		holder.mDate.setText(newsItem.getDate());
		if (newsItem.getImgLink() != null) {
			holder.mImg.setVisibility(View.VISIBLE);
			imageLoader.displayImage(newsItem.getImgLink(), holder.mImg,
					options);
		} else {
			holder.mImg.setVisibility(View.GONE);
		}

		return convertView;
	}

	private final class ViewHolder {
		TextView mTitle;
		TextView mContent;
		ImageView mImg;
		TextView mDate;
	}
}