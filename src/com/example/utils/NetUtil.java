package com.example.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.lidroid.xutils.http.client.HttpRequest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetUtil {
	/**
	 * ��鵱ǰ�ֻ�����
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context) {
		// �ж����ӷ�ʽ
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMOBILEConnected(context);
		if (wifiConnected == false && mobileConnected == false) {
			// �����û�����ӷ���false����ʾ�û���ǰû������
			return false;
		}
		return true;
	}

	// �ж��ֻ�ʹ����wifi����mobile
	/**
	 * �ж��ֻ��Ƿ����wifi����
	 */
	public static boolean isWIFIConnected(Context context) {
		// Context.CONNECTIVITY_SERVICE).

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public static boolean isMOBILEConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public static String sendRequestWithhttpClient(String url) {
		String response = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpclient.execute(httpget);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				response = EntityUtils.toString(entity);

				Log.i("gxl", response);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
}
