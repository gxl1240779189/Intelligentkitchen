package com.example.adapter;

import java.util.List;

import com.example.httputil.FoodsItem;
import com.example.httputil.NewsItem;
import com.example.intelligentkitchenn.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodsItemAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<FoodsItem> mDatas;

	/**
	 * ʹ����github��Դ��ImageLoad��������ݼ���
	 */
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public FoodsItemAdapter(Context context, List<FoodsItem> datas) {
		this.mDatas = datas;
		mInflater = LayoutInflater.from(context);

		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.xiaolian)
				.showImageForEmptyUri(R.drawable.xiaolian)
				.showImageOnFail(R.drawable.xiaolian).cacheInMemory()
				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}

	public List<FoodsItem> returnmDatas() {
		return this.mDatas;
	}

	public void addAll(List<FoodsItem> mDatas) {
		this.mDatas.addAll(mDatas);
	}

	public void setDatas(List<FoodsItem> mDatas) {
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
			convertView = mInflater.inflate(R.layout.foodlistviewitem, null);
			holder = new ViewHolder();

			holder.mContent = (TextView) convertView
					.findViewById(R.id.food_content);
			holder.mTitle = (TextView) convertView.findViewById(R.id.food_title);
			holder.mImg = (ImageView) convertView.findViewById(R.id.food_newsImg);
			holder.mPersonname= (TextView) convertView.findViewById(R.id.food_personname);
			holder.mDate=(TextView) convertView.findViewById(R.id.food_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FoodsItem foodsitem = mDatas.get(position);
		holder.mTitle.setText(foodsitem.getTitle());
		holder.mContent.setText(foodsitem.getContent());
		holder.mPersonname.setText(foodsitem.getWriter());	
		holder.mDate.setText(foodsitem.getDate());	
	    imageLoader.displayImage(foodsitem.getImgLink(), holder.mImg,
					options);
		return convertView;
	}

	private final class ViewHolder {
		TextView mTitle;
		TextView mContent;
		ImageView mImg;
		TextView mPersonname;
		TextView mDate;
	}

}
