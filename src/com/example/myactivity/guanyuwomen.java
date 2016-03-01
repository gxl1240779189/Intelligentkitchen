package com.example.myactivity;

import com.example.intelligentkitchen.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class guanyuwomen extends Activity {

	public ImageView houtui;
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.guanyuwomen);
	houtui=(ImageView) findViewById(R.id.houtui);
	houtui.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
}
}
