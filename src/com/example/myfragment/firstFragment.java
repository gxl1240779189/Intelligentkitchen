package com.example.myfragment;

import java.util.ArrayList;
import java.util.List;



import com.example.intelligentkitchenn.R;
import com.example.paintcanvas.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class firstFragment extends android.support.v4.app.Fragment implements OnClickListener{
	private EditText search;
	private SlidingMenu mMenu;
	private ImageView openmenu;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.firstlayout, container, false);
		search=(EditText) v.findViewById(R.id.search);
		search.setOnClickListener(this);
		mMenu = (SlidingMenu)v.findViewById(R.id.id_menu);
		openmenu=(ImageView) v.findViewById(R.id.menu);
		openmenu.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search:
			startActivity(new Intent(getActivity(), com.example.myactivity.search.class));
			break;

		case R.id.menu:
			mMenu.toggle();
			break;
		}
	}

	
}
