package com.example.httputil;

import com.example.httputil.News.NewsType;

public class Foodcontent {
	public String num;
	public String imagelink;
	public String teachtext;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getImagelink() {
		return imagelink;
	}

	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}

	public String getTeachtext() {
		return teachtext;
	}

	public void setTeachtext(String teachtext) {
		this.teachtext = teachtext;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return imagelink+"  "+teachtext;
	}
}
