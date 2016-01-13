package com.example.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.example.httputil.FoodFuliao;


import com.example.intelligentkitchenn.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FoodfuliaoAdapter extends ArrayAdapter<FoodFuliao> {
	public int textViewResourceId;

	public FoodfuliaoAdapter(Context context, int textViewResourceId,
			List<FoodFuliao> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.textViewResourceId = textViewResourceId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		FoodFuliao fuliao = getItem(position);
		View view;
		Viewholder viewholder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(
					R.layout.foodfuliaoitem, null);
			viewholder = new Viewholder();
			viewholder.name = (TextView) view
					.findViewById(R.id.food_fuliao_name);
			viewholder.shuliang = (TextView) view
					.findViewById(R.id.food_fuliao_shuliang);
			view.setTag(viewholder);
		} else {
			view = convertView;
			viewholder = (Viewholder) view.getTag();
		}
		viewholder.name.setText(fuliao.getFuliaoname());
		viewholder.shuliang.setText(fuliao.getFuliaoshuliang());
		return view;
	}

	class Viewholder {
		TextView name;
		TextView shuliang;
	}

}
