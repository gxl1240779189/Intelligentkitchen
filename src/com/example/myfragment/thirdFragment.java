package com.example.myfragment;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.adapter.FoodfuliaoAdapter;
import com.example.adapter.FoodteachAdapter;
import com.example.httputil.CommonException;
import com.example.httputil.FoodFuliao;
import com.example.httputil.Foodcontent;
import com.example.httputil.FoodsItem;
import com.example.httputil.FoodsItemBiz;
import com.example.httputil.SliderShowViewItem;
import com.example.httputil.SliderShowViewItemBiz;
import com.example.intelligentkitchen.R;
import com.example.other.StringtoURL;

import com.example.ui.SlidingMenu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class thirdFragment extends android.support.v4.app.Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.thirdfragment, container, false);

		return v;
	}

}
