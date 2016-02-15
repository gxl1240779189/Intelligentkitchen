package com.example.myactivity;


import com.example.intelligentkitchenn.R;
import com.example.myfragment.firstFragment;
import com.example.myfragment.foodFragment;
import com.example.myfragment.fourthFragment;
import com.example.myfragment.secondFragment;
import com.example.myfragment.thirdFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private firstFragment first;
	private secondFragment second;
	private foodFragment food;
	private thirdFragment third;
	private fourthFragment fourth;
	private View first_layout;
	private View second_layout;
//	private View third_layout;
	private View fourth_layout;
	private ImageView first_image;
	private ImageView second_image;
//	private ImageView third_image;
	private ImageView fourth_image;
	private TextView first_text;
	private TextView second_text;
//	private TextView third_text;
	private TextView fourth_text;
	public static android.support.v4.app.FragmentManager fragmentmanager;
	public static android.support.v4.app.FragmentTransaction fragmenttransaction;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
		first_layout.setOnClickListener(this);
		second_layout.setOnClickListener(this);
//		third_layout.setOnClickListener(this);
		fourth_layout.setOnClickListener(this);

	}

	@SuppressLint("Recycle")
	void init() {
		first_layout = findViewById(R.id.page1);
		second_layout = findViewById(R.id.page2);
//		third_layout = findViewById(R.id.page3);
		fourth_layout = findViewById(R.id.page4);
		first_image = (ImageView) findViewById(R.id.page1_image);
		second_image = (ImageView) findViewById(R.id.page2_image);
//		third_image = (ImageView) findViewById(R.id.page3_image);
		fourth_image = (ImageView) findViewById(R.id.page4_image);
		first_text = (TextView) findViewById(R.id.page1_text);
		second_text = (TextView) findViewById(R.id.page2_text);
//		third_text = (TextView) findViewById(R.id.page3_text);
		fourth_text = (TextView) findViewById(R.id.page4_text);
		first_image.setImageResource(R.drawable.main_recipe_red);
		first_text.setTextColor(Color.parseColor("#FD7575"));
		fragmentmanager = getSupportFragmentManager();
		fragmenttransaction = fragmentmanager.beginTransaction();
		first = new firstFragment();
		fragmenttransaction.replace(R.id.content, first);
		fragmenttransaction.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		clearview();
		android.support.v4.app.FragmentTransaction fragment1 = fragmentmanager
				.beginTransaction();
		switch (v.getId()) {
		case R.id.page1:
			first_image.setImageResource(R.drawable.main_recipe_red);
			first_text.setTextColor(Color.parseColor("#FD7575"));
			hideFragments(fragment1);
			if (first != null) {
				fragment1.show(first);
			} else {
				first = new firstFragment();
				fragment1.add(R.id.content, first);
			}
			break;

		case R.id.page2:
			clearview();
			second_image.setImageResource(R.drawable.main_home_red);
			second_text.setTextColor(Color.parseColor("#FD7575"));
			hideFragments(fragment1);		
			if (second != null) {
				fragment1.show(second);
			} else {
				second = new secondFragment();
				fragment1.add(R.id.content, second);
			}
			break;

//		case R.id.page3:
//			clearview();
//			third_image.setImageResource(R.drawable.main_mofang_red);
//			third_text.setTextColor(Color.parseColor("#FD7575"));
//			hideFragments(fragment1);
//			if (third != null) {
//				fragment1.show(third);
//			} else {
//				third = new thirdFragment();
//				fragment1.add(R.id.content, third);
//			}
//			break;

		case R.id.page4:
			clearview();
			fourth_image.setImageResource(R.drawable.main_user_red);
			fourth_text.setTextColor(Color.parseColor("#FD7575"));
			hideFragments(fragment1);
			if (fourth != null) {
				fragment1.show(fourth);
			} else {
				fourth = new fourthFragment();
				fragment1.add(R.id.content, fourth);
			} 
			break;
	
		}
		fragment1.commit();

	}

	public void clearview() {
		first_image.setImageResource(R.drawable.main_recipe_gray);
		first_text.setTextColor(Color.parseColor("#828383"));
		second_image.setImageResource(R.drawable.main_home_gray);
		second_text.setTextColor(Color.parseColor("#828383"));
//		third_image.setImageResource(R.drawable.main_mofang_gray);
//		third_text.setTextColor(Color.parseColor("#828383"));
		fourth_image.setImageResource(R.drawable.main_user_gray);
		fourth_text.setTextColor(Color.parseColor("#828383"));

	}

	private void hideFragments(
			android.support.v4.app.FragmentTransaction fragment) {
		if (first != null) {
			fragment.hide(first);
		}
		if (second != null) {
			fragment.hide(second);
		}
//		if (third != null) {
//			fragment.hide(third);
//		}
		if (fourth != null) {
			fragment.hide(fourth);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
	}
}
