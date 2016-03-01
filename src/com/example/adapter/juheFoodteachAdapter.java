package com.example.adapter;

import java.util.List;

import com.example.adapter.FoodteachAdapter.Viewholder;
import com.example.httputil.Foodcontent;
import com.example.httputil.JsonBean.Steps;
import com.example.intelligentkitchen.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class juheFoodteachAdapter extends BaseAdapter {

	List<Steps> steps;
	Context context;
	public int textViewResourceId;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public juheFoodteachAdapter(Context context, List<Steps> steps,
			int textViewResourceId) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.steps = steps;
		this.textViewResourceId = textViewResourceId;
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.xiaolian)
				.showImageForEmptyUri(R.drawable.xiaolian)
				.showImageOnFail(R.drawable.xiaolian).cacheInMemory()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return steps.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return steps.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Steps item = steps.get(position);
		int num = ++position;
		View view = null;
		Viewholder viewholder;
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(textViewResourceId,
					null);
			viewholder = new Viewholder();
			viewholder.num = (TextView) view.findViewById(R.id.num);
			viewholder.teachimage = (ImageView) view
					.findViewById(R.id.teach_image);
			viewholder.teachtext = (TextView) view
					.findViewById(R.id.teach_text);
			view.setTag(viewholder);
		} else {
			view = convertView;
			viewholder = (Viewholder) view.getTag();
		}
		viewholder.num.setText(num + "");
		viewholder.teachimage.setVisibility(View.VISIBLE);
		imageLoader.displayImage(item.getImg(), viewholder.teachimage, options);
		viewholder.teachtext.setText(item.getStep());
		return view;
	}

	class Viewholder {
		TextView num;
		ImageView teachimage;
		TextView teachtext;
	}

}
