package com.example.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class mySharedpreferencesHelper {
	Context context;
	SharedPreferences searchHistory;

	public mySharedpreferencesHelper(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		searchHistory = context.getSharedPreferences("search_history", 0);
	}

	public void save(String searchneirong) {
		String old_text = searchHistory.getString("history", "暂时没有搜索记录");
		if (old_text == "暂时没有搜索记录")
			old_text = "";
		// 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
		StringBuilder builder = new StringBuilder(old_text);
		builder.append(searchneirong + ",");

		// 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
		if (!old_text.contains(searchneirong + ",")) {
			SharedPreferences.Editor myeditor = searchHistory.edit();
			myeditor.putString("history", builder.toString());
			myeditor.commit();
			// Toast.makeText(context, searchneirong + "添加成功",
			// Toast.LENGTH_SHORT)
			// .show();
		} else {
			// Toast.makeText(context, searchneirong + "已存在",
			// Toast.LENGTH_SHORT)
			// .show();
		}
	}

	public List<String> returnhistroydata() {
		List<String> histroy_list = new ArrayList<String>();
		String history = searchHistory.getString("history", "暂时没有搜索记录");
		if (!(history == "暂时没有搜索记录")) {
			String[] history_arr = history.split(",");
			if (history_arr.length != 0) {
				for (int i = 0; i < history_arr.length; i++) {
					histroy_list.add(history_arr[i]);
				}
			}
		}
		return histroy_list;
	}

	public void delete(String deleteneirong) {

	}

	public void cleanHistory() {
		SharedPreferences.Editor editor = searchHistory.edit();
		editor.clear();
		editor.commit();
		Toast.makeText(context, "清除成功", Toast.LENGTH_SHORT).show();
	}
}
