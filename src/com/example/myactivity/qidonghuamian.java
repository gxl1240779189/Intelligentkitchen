package com.example.myactivity;

import com.example.intelligentkitchen.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class qidonghuamian extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qidonghuamian);
		Handler x = new Handler();
		x.postDelayed(new splashhandler(), 2000);
	}

	class splashhandler implements Runnable {
		public void run() {
			startActivity(new Intent(getApplication(), MainActivity.class));
			qidonghuamian.this.finish();
		}
	}
}
