package com.example.adapter;

import java.util.List;

import com.example.httputil.FoodsItem;
import com.example.httputil.JsonBean.foodItem;
import com.example.intelligentkitchen.R;
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

public class juhelistviewAdapter extends BaseAdapter {
	List<foodItem> list;
	Context context;
	LayoutInflater mInflater;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public juhelistviewAdapter(Context context, List<foodItem> list) {
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.xiaolian)
				.showImageForEmptyUri(R.drawable.xiaolian)
				.showImageOnFail(R.drawable.xiaolian).cacheInMemory()
				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.juhelistviewitem, null);
			holder = new ViewHolder();
			holder.mContent = (TextView) convertView
					.findViewById(R.id.mContent);
			holder.mTitle = (TextView) convertView.findViewById(R.id.mTitle);
			holder.mImg = (ImageView) convertView.findViewById(R.id.mImg);
			holder.tags = (TextView) convertView.findViewById(R.id.tags);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		foodItem fooditem = list.get(position);
		holder.mTitle.setText(fooditem.getTitle());
		holder.mContent.setText(fooditem.getImtro());
		imageLoader.displayImage(fooditem.getAlbums().get(0), holder.mImg,
				options);
		return convertView;
	}

	private final class ViewHolder {
		TextView mTitle; // 菜名
		TextView mContent; // "韭菜具健胃、提神、止汗固涩、补肾助阳、固精等功效，鸡蛋是人类最好的营养来源之一，鸡蛋中含有大量的维生素和矿物质及有高生物价值的蛋白质。韭菜与鸡蛋相炒，既营养又美味"
		ImageView mImg; // 图片地址
		TextView tags; // 增强免疫力;家常菜;懒人食谱;粤菜;快手菜;炒;午餐",
	}

}
