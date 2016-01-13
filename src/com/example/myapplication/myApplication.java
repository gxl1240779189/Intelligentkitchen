package com.example.myapplication;

import android.app.Application;
import android.content.Context;

public class myApplication extends Application {
	public static Context context;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		this.context = getApplicationContext();
		super.onCreate();
	}

	public static Context GetContext() {
		return context;
	}

}
